package com.github.basva923.garminphoneactivity.performancemonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.github.basva923.garminphoneactivity.controller.ActivityController
import com.github.basva923.garminphoneactivity.controller.Controllers
import com.github.basva923.garminphoneactivity.garmin.MockActivityControl
import com.github.basva923.garminphoneactivity.model.Model
import com.github.basva923.garminphoneactivity.performancemonitor.session.SessionScreen
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.GarminPhoneActivityTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActivity()
    enableEdgeToEdge()
    setContent {
      GarminPhoneActivityTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          SessionScreen(Modifier.padding(innerPadding))
        }
      }
    }
  }

  private fun setupActivity() {
//    Controllers.activityController = ActivityController(
//      Model.track, GarminActivityControl(
//        GarminConnection(this)
//      )
//    )
    Controllers.activityController = ActivityController(Model.track, MockActivityControl())
  }
}