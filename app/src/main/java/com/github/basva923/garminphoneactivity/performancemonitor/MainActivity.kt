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
import com.github.basva923.garminphoneactivity.garmin.GarminActivityControl
import com.github.basva923.garminphoneactivity.garmin.GarminConnection
import com.github.basva923.garminphoneactivity.garmin.MockActivityControl
import com.github.basva923.garminphoneactivity.model.Model
import com.github.basva923.garminphoneactivity.performancemonitor.session.SessionScreen
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.GarminPhoneActivityTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      GarminPhoneActivityTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          SessionScreen(Modifier.padding(innerPadding))
        }
      }
    }
  }

}