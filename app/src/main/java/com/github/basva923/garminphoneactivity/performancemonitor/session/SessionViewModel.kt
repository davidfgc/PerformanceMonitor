package com.github.basva923.garminphoneactivity.performancemonitor.session

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.basva923.garminphoneactivity.performancemonitor.sensorsdata.PhoneActivityAdapter
import com.github.basva923.garminphoneactivity.performancemonitor.shared.ConnectionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel: ViewModel() {

  private val _uiState = MutableStateFlow<SessionUiState>(SessionUiState.Loading)
  val uiState = _uiState.asStateFlow()

  private val _sessionData = MutableStateFlow<SessionData>(EmptySessionData())
  val sessionData = _sessionData.asStateFlow()

  private lateinit var phoneActivityAdapter: PhoneActivityAdapter

  fun initialize(context: Context) {
    viewModelScope.launch { initializePhoneActivityAdapter(context) }
  }

  private fun initializePhoneActivityAdapter(context: Context) {
    phoneActivityAdapter = PhoneActivityAdapter().apply {
      initialize(context, isMock = true) {
        when (it) {
          is ConnectionResult.Error -> { onConnectionError(it.message) }
          is ConnectionResult.Success -> { onConnectionSuccess() }
        }
      }
    }
  }

  private fun onConnectionSuccess() {
    viewModelScope.launch {
      _uiState.emit(SessionUiState.Success)
      phoneActivityAdapter.sensorsDataFlow.collectLatest {
        _sessionData.emit(it)
      }
    }
  }

  private fun onConnectionError(message: String) {
    viewModelScope.launch { _uiState.emit(SessionUiState.Error(message)) }
  }

  override fun onCleared() {
    super.onCleared()
    phoneActivityAdapter.unregister()
  }
}

sealed class SessionUiState {
  data object Loading: SessionUiState()
  data object Success: SessionUiState()
  data class Error(val message: String): SessionUiState()
}
