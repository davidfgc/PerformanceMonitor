package com.github.basva923.garminphoneactivity.performancemonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.basva923.garminphoneactivity.controller.ActivityController
import com.github.basva923.garminphoneactivity.controller.Controllers
import com.github.basva923.garminphoneactivity.garmin.GarminActivityControl
import com.github.basva923.garminphoneactivity.garmin.GarminConnection
import com.github.basva923.garminphoneactivity.model.Model
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.GarminPhoneActivityTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActivity()
    enableEdgeToEdge()
    setContent {
      GarminPhoneActivityTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }

  private fun setupActivity() {
    Controllers.activityController = ActivityController(
      Model.track, GarminActivityControl(
        GarminConnection(this)
      )
    )
  }
}

@Composable
fun Greeting(
  viewModel: MainViewModel = viewModel(),
  modifier: Modifier = Modifier
) {

  val heartRate by viewModel.heartRate.collectAsState()
  val heatRateZone by viewModel.zone.collectAsState()
  val time by viewModel.time.collectAsState()
  val altitude by viewModel.altitude.collectAsState()

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "HR $heartRate!",
      style = MaterialTheme.typography.displayMedium
    )
    Text(
      text = "Zone: $heatRateZone",
      style = MaterialTheme.typography.displayMedium
    )
    Text(
      text = "Time: $time",
      style = MaterialTheme.typography.displayMedium
    )
    Text(
      text = "Altitude: $altitude",
      style = MaterialTheme.typography.displayMedium
    )
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  GarminPhoneActivityTheme {
    Greeting()
  }
}