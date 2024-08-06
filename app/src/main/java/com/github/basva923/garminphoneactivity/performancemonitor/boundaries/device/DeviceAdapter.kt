package com.github.basva923.garminphoneactivity.performancemonitor.boundaries.device

import android.content.Context
import com.github.basva923.garminphoneactivity.controller.ActivityController
import com.github.basva923.garminphoneactivity.controller.Controllers
import com.github.basva923.garminphoneactivity.garmin.GarminActivityControl
import com.github.basva923.garminphoneactivity.garmin.GarminConnection
import com.github.basva923.garminphoneactivity.garmin.MockActivityControl
import com.github.basva923.garminphoneactivity.model.LiveTrackProperty
import com.github.basva923.garminphoneactivity.model.Model
import com.github.basva923.garminphoneactivity.model.ModelUpdateReceiver
import com.github.basva923.garminphoneactivity.model.PropertyType
import com.github.basva923.garminphoneactivity.performancemonitor.boundaries.garmin.GarminError
import com.github.basva923.garminphoneactivity.performancemonitor.session.EmptySessionData
import com.github.basva923.garminphoneactivity.performancemonitor.session.SessionData
import com.github.basva923.garminphoneactivity.performancemonitor.shared.AppResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeviceAdapter : ModelUpdateReceiver {

  private var lastSessionData: SessionData = EmptySessionData()

  val sensorsDataFlow: Flow<SessionData> = flow {
    while (true) {

      if (lastSessionData !is EmptySessionData) {
        emit(lastSessionData)
      }
      delay(1000)
    }
  }

  fun initialize(context: Context, isMock: Boolean = false, onResult: (AppResult<Unit, DeviceError>) -> Unit = {}) {
    if (isMock) {
      Controllers.activityController = ActivityController(Model.track, MockActivityControl())
      register()
      onResult(AppResult.Success(Unit))
    } else {
      val garminConnection = GarminConnection(context)
      garminConnection.initialize(context, false) { it: AppResult<Unit, GarminError> ->
        when (it) {
          is AppResult.Error -> onResult(AppResult.Error(DeviceError.SETUP))
          is AppResult.Success -> {
            Controllers.activityController = ActivityController(
              Model.track, GarminActivityControl(garminConnection)
            )
            register()
            onResult(AppResult.Success(Unit))
          }
        }
      }
    }
  }

  private fun register() {
    Model.modelUpdateReceivers.add(this)
  }

  fun unregister() {
    Model.modelUpdateReceivers.remove(this)
  }

  override fun onModelUpdate()  {
    Model.track.liveTrackInfo.hrZoneFinder.valueToZone(0)
    lastSessionData = object: SessionData {
      override val heartRate: Int = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.HEART_RATE).toInt()
      override val heartRateZone: Int = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.HEART_RATE_ZONE).toInt()
      override val time: Int = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.TIME).toInt()
      override val cadence: Int = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.CADENCE).toInt()
      override val speed: Float = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.SPEED).toFloat()
      override val distance: Float = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.DISTANCE).toFloat()
      override val latitude: Double = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.LATITUDE)
      override val longitude: Double = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.LONGITUDE)
    }
  }
}