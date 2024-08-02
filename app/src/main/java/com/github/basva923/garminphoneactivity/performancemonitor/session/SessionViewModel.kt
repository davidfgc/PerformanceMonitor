package com.github.basva923.garminphoneactivity.performancemonitor.session

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.basva923.garminphoneactivity.performancemonitor.boundaries.device.DeviceAdapter
import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.HeartRateZones
import com.github.basva923.garminphoneactivity.performancemonitor.settings.SettingsRepository
import com.github.basva923.garminphoneactivity.performancemonitor.settings.SettingsRepositoryImpl
import com.github.basva923.garminphoneactivity.performancemonitor.shared.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel(
  settingsRepository: SettingsRepository = SettingsRepositoryImpl()
): ViewModel() {

  private val targetZones: IntRange = settingsRepository.getHeartRateTargetZones()
  val lines: List<Pair<Pair<Int, Int>, Color>> = HeartRateZones().getTargetZonesColor(targetZones.toList())

  private val inTargetColor = 0xFF228B22
  private val outOfTargetColor = 0xFFDC3B61

  private val _uiState = MutableStateFlow<SessionUiState>(SessionUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val _sessionData = MutableStateFlow<SessionData>(EmptySessionData())
  val sessionData = _sessionData.asStateFlow()

  private val _backgroundColor = MutableStateFlow<Long>(0)
  val backgroundColor: StateFlow<Long> = _backgroundColor.asStateFlow()

  private lateinit var deviceAdapter: DeviceAdapter

  fun initialize(context: Context) {
    viewModelScope.launch { initializePhoneActivityAdapter(context) }
  }

  private fun initializePhoneActivityAdapter(context: Context) {
    deviceAdapter = DeviceAdapter().apply {
      initialize(context, isMock = true) {
        when (it) {
          is AppResult.Error -> onConnectionError("ERROR: ${it.error.name}")
          is AppResult.Success -> { onConnectionSuccess() }
        }
      }
    }
  }

  private fun onConnectionSuccess() {
    viewModelScope.launch {
      _uiState.emit(SessionUiState.Success)
      deviceAdapter.sensorsDataFlow.collectLatest {
        _sessionData.emit(it)
        _backgroundColor.emit(if (it.heartRateZone in targetZones) inTargetColor else outOfTargetColor)
      }
    }
  }

  private fun onConnectionError(message: String) {
    viewModelScope.launch { _uiState.emit(SessionUiState.Error(message)) }
  }

  override fun onCleared() {
    super.onCleared()
    deviceAdapter.unregister()
  }
}

sealed class SessionUiState {
  data object Loading: SessionUiState()
  data object Success: SessionUiState()
  data class Error(val message: String): SessionUiState()
}
