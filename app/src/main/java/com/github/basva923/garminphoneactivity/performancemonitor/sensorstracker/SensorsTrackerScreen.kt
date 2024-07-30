package com.github.basva923.garminphoneactivity.performancemonitor.sensorstracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SensorsTrackerScreen(modifier: Modifier = Modifier, viewModel: SensorsTrackerViewModel = viewModel()) {
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