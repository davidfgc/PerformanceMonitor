package com.github.basva923.garminphoneactivity.performancemonitor.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.ui.HeartRateTargetZones
import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.domain.HeartRateZone
import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.domain.UserHeartRate
import com.github.basva923.garminphoneactivity.settings.Settings
import java.util.Locale

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
    is SessionUiState.Success -> SessionLayout(
      sessionData,
      viewModel.targetZones,
      modifier.background(Color(backgroundColor))
    )
    is SessionUiState.Error -> SessionErrorLayout((uiState as SessionUiState.Error).message, modifier)
    is SessionUiState.Loading -> SessionLoadingLayout(modifier)
  }
}

@Composable
private fun SessionLayout(
  sessionData: SessionData,
  targetZones: List<HeartRateZone>,
  modifier: Modifier = Modifier,
) {
  val markerPosition = UserHeartRate(Settings.ftpHeartRate).getPercentage(sessionData.heartRate)
  Box {
    Column(
      modifier = modifier
        .fillMaxSize(),
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
      Text(text = "Cadence: ${sessionData.cadence}", style = MaterialTheme.typography.displayMedium)
      Text(
        text = "Speed: ${String.format(Locale.US, "%.2f", sessionData.speed)}",
        style = MaterialTheme.typography.displayMedium
      )
      Text(
        text = "Latitude: ${String.format(Locale.US, "%.2f", sessionData.latitude)}",
        style = MaterialTheme.typography.displayMedium
      )
      Text(
        text = "Longitude: ${String.format(Locale.US, "%.2f", sessionData.longitude)}",
        style = MaterialTheme.typography.displayMedium
      )
    }

    HeartRateTargetZones(
      markerPosition = markerPosition,
      modifier = Modifier
        .safeDrawingPadding()
        .align(Alignment.BottomCenter)
        .drawBehind {
          drawLine(Color.White, start = Offset(0f, 0f), end = Offset(size.width, 0f), strokeWidth = 1.dp.toPx())
        },
      targetZones = targetZones,
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
