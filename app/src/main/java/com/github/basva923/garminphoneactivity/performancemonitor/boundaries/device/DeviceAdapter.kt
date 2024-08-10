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
import com.github.basva923.garminphoneactivity.performancemonitor.session.SessionData
import com.github.basva923.garminphoneactivity.performancemonitor.shared.AppResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf

class DeviceAdapter {

  fun initialize(context: Context, isMock: Boolean = false, onResult: (AppResult<Unit, DeviceError>) -> Unit = {}): Flow<SessionData> {
    var res: Flow<SessionData> = flowOf()

    if (isMock) {
      Controllers.activityController = ActivityController(Model.track, MockActivityControl())
      onResult(AppResult.Success(Unit))
      res = observeDeviceData()
    } else {
      val garminConnection = GarminConnection(context)
      garminConnection.initialize(context, false) { it: AppResult<Unit, GarminError> ->
        when (it) {
          is AppResult.Error -> onResult(AppResult.Error(DeviceError.SETUP))
          is AppResult.Success -> {
            Controllers.activityController = ActivityController(
              Model.track, GarminActivityControl(garminConnection)
            )
            onResult(AppResult.Success(Unit))
            res = observeDeviceData()
          }
        }
      }
    }

    return res
  }

  private fun observeDeviceData(): Flow<SessionData> {
    return callbackFlow {
      Model.modelUpdateReceivers.add(object: ModelUpdateReceiver {
        override fun onModelUpdate() {
          val sessionData = object: SessionData {
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

          trySend(sessionData)
        }
      })

      awaitClose {
        Model.modelUpdateReceivers.clear()
      }
    }
  }

}