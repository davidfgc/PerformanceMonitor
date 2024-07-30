package com.github.basva923.garminphoneactivity.performancemonitor.sensorsdata

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
import com.github.basva923.garminphoneactivity.performancemonitor.session.EmptySessionData
import com.github.basva923.garminphoneactivity.performancemonitor.session.SessionData
import com.github.basva923.garminphoneactivity.performancemonitor.shared.ConnectionResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhoneActivityAdapter : ModelUpdateReceiver {

  private var lastSessionData: SessionData = EmptySessionData()

  val sensorsDataFlow: Flow<SessionData> = flow {
    while (true) {

      if (lastSessionData !is EmptySessionData) {
        emit(lastSessionData)
      }
      delay(1000)
    }
  }

  fun initialize(context: Context, isMock: Boolean = true, onResult: (ConnectionResult) -> Unit = {}) {
    if (isMock) {
      Controllers.activityController = ActivityController(Model.track, MockActivityControl())
      register()
      onResult(ConnectionResult.Success)
    } else {
      val garminConnection = GarminConnection(context)
      garminConnection.initialize(context, false) {
        when (it) {
          is ConnectionResult.Error -> { onResult(it) }
          is ConnectionResult.Success -> {
            Controllers.activityController = ActivityController(
              Model.track, GarminActivityControl(garminConnection)
            )
            register()
            onResult(ConnectionResult.Success)
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
      override val altitude: Int = Model.track.liveTrackInfo
        .getValue(PropertyType.CURRENT, LiveTrackProperty.ALTITUDE).toInt()
    }
  }

}