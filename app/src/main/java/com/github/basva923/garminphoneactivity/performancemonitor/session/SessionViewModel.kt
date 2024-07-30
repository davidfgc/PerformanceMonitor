package com.github.basva923.garminphoneactivity.performancemonitor.session

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.basva923.garminphoneactivity.performancemonitor.sensorsdata.PhoneActivityAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel: ViewModel() {

  private val _sessionData = MutableStateFlow<SessionData>(EmptySessionData())
  val sessionData = _sessionData.asStateFlow()

  private lateinit var phoneActivityAdapter: PhoneActivityAdapter

  fun register(context: Context) {
    phoneActivityAdapter = PhoneActivityAdapter(context).apply {
      register()
    }
    viewModelScope.launch {
      phoneActivityAdapter.sensorsDataFlow.collectLatest {
        _sessionData.emit(it)
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    phoneActivityAdapter.unregister()
  }
}
