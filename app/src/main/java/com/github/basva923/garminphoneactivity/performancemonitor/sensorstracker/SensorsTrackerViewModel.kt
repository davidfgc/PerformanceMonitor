package com.github.basva923.garminphoneactivity.performancemonitor.sensorstracker

import androidx.lifecycle.ViewModel
import com.github.basva923.garminphoneactivity.model.LiveTrackProperty
import com.github.basva923.garminphoneactivity.model.Model
import com.github.basva923.garminphoneactivity.model.ModelUpdateReceiver
import com.github.basva923.garminphoneactivity.model.PropertyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SensorsTrackerViewModel: ViewModel(), ModelUpdateReceiver {
  private val _heartRate = MutableStateFlow(0)
  val heartRate: StateFlow<Int> = _heartRate.asStateFlow()

  private val _zone = MutableStateFlow(0)
  val zone: StateFlow<Int> = _zone.asStateFlow()

  private val _time = MutableStateFlow(0)
  val time: StateFlow<Int> = _time.asStateFlow()

  private val _altitude = MutableStateFlow(0)
  val altitude: StateFlow<Int> = _altitude.asStateFlow()

  init {
    Model.modelUpdateReceivers.add(this)
  }

  override fun onModelUpdate() {
    _heartRate.value = Model.track.liveTrackInfo
      .getValue(PropertyType.CURRENT, LiveTrackProperty.HEART_RATE).toInt()

    _time.value = Model.track.liveTrackInfo
      .getValue(PropertyType.CURRENT, LiveTrackProperty.TIME).toInt()

    _altitude.value = Model.track.liveTrackInfo
      .getValue(PropertyType.CURRENT, LiveTrackProperty.ALTITUDE).toInt()

    _zone.value = Model.track.liveTrackInfo
      .getValue(PropertyType.CURRENT, LiveTrackProperty.HEART_RATE_ZONE).toInt()
  }
}
