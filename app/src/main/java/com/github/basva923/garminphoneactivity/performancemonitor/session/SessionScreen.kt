package com.github.basva923.garminphoneactivity.performancemonitor.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SessionScreen(modifier: Modifier = Modifier, viewModel: SessionViewModel = viewModel()) {
  val sessionData by viewModel.sessionData.collectAsState()

  val context = LocalContext.current
  LaunchedEffect(key1 = Unit) {
    viewModel.register(context)
  }

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "HR ${sessionData.heartRate}!",
      style = MaterialTheme.typography.displayMedium
    )
    Text(
      text = "Zone: ${sessionData.heartRateZone}",
      style = MaterialTheme.typography.displayMedium
    )
    Text(
      text = "Time: ${sessionData.time}",
      style = MaterialTheme.typography.displayMedium
    )
    Text(
      text = "Altitude: ${sessionData.altitude}",
      style = MaterialTheme.typography.displayMedium
    )
  }
}