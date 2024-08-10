package com.github.basva923.garminphoneactivity.performancemonitor.session

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.basva923.garminphoneactivity.performancemonitor.boundaries.device.DeviceAdapter
import com.github.basva923.garminphoneactivity.performancemonitor.boundaries.device.DeviceError
import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.domain.HeartRateZone
import com.github.basva923.garminphoneactivity.performancemonitor.settings.SettingsRepository
import com.github.basva923.garminphoneactivity.performancemonitor.settings.SettingsRepositoryImpl
import com.github.basva923.garminphoneactivity.performancemonitor.shared.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SessionViewModel(
  settingsRepository: SettingsRepository = SettingsRepositoryImpl()
): ViewModel() {

  private val targetZonesRange: IntRange = settingsRepository.getHeartRateTargetZones()
  val targetZones = listOf(HeartRateZone.EASY, HeartRateZone.AEROBIC)

  private val inTargetColor = 0xFF228B22
  private val outOfTargetColor = 0xFFDC3B61

  private val _uiState = MutableStateFlow<SessionUiState>(SessionUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val _sessionData = MutableStateFlow<SessionData>(EmptySessionData())
  val sessionData = _sessionData.asStateFlow()

  private val _backgroundColor = MutableStateFlow<Long>(0)
  val backgroundColor: StateFlow<Long> = _backgroundColor.asStateFlow()

  fun initialize(context: Context) {
    DeviceAdapter().initialize(context, isMock = true, ::onResult)
      .onEach {
        _sessionData.emit(it)
        _backgroundColor.emit(if (it.heartRateZone in targetZonesRange) inTargetColor else outOfTargetColor)
      }.launchIn(viewModelScope)
  }

  private fun onResult(appResult: AppResult<Unit, DeviceError>) {
    when (appResult) {
      is AppResult.Error -> {
        _uiState.value = SessionUiState.Error("Error initializing device")
      }
      is AppResult.Success -> {
        _uiState.value = SessionUiState.Success
      }
    }
  }
}

sealed class SessionUiState {
  data object Loading: SessionUiState()
  data object Success: SessionUiState()
  data class Error(val message: String): SessionUiState()
}
