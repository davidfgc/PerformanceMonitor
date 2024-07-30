package com.github.basva923.garminphoneactivity.performancemonitor.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SessionScreen(modifier: Modifier = Modifier, viewModel: SessionViewModel = viewModel()) {
  val uiState by viewModel.uiState.collectAsState()
  val sessionData by viewModel.sessionData.collectAsState()
  val backgroundColor by viewModel.backgroundColor.collectAsState()

  val context = LocalContext.current
  LaunchedEffect(key1 = Unit) {
    viewModel.initialize(context)
  }

  when (uiState) {
    is SessionUiState.Success -> SessionLayout(sessionData, modifier.background(Color(backgroundColor)))
    is SessionUiState.Error -> SessionErrorLayout((uiState as SessionUiState.Error).message, modifier)
    is SessionUiState.Loading -> SessionLoadingLayout(modifier)
  }
}

@Composable
private fun SessionLayout(
  sessionData: SessionData,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "HR: ${sessionData.heartRate}",
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

@Composable
fun SessionErrorLayout(message: String, modifier: Modifier = Modifier) {
  Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text(text = message, textAlign = TextAlign.Center)
  }
}

@Composable
fun SessionLoadingLayout(modifier: Modifier = Modifier) {
  Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
  }
}
