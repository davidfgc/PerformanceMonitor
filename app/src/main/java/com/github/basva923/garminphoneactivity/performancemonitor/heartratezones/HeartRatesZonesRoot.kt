package com.github.basva923.garminphoneactivity.performancemonitor.heartratezones

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.GarminPhoneActivityTheme
import kotlin.math.min

@Composable
fun HeartRatesZonesRoot(
  markerPosition: Int,
  modifier: Modifier = Modifier,
  lines: List<Pair<Pair<Int, Int>, Color>> = HeartRateZones().getHeartRateZonesColors()
) {
  HeartRateZones(markerPosition, lines = lines, modifier)
}

@Composable
fun HeartRateZones(markerPosition: Int, lines: List<Pair<Pair<Int, Int>, Color>>, modifier: Modifier = Modifier, height: Dp = 10.dp) {
  Canvas(modifier = modifier
    .fillMaxWidth()
    .height(height)) {
    lines.forEachIndexed{ index, (position, color) ->
      val startX: Float = if (index == 0) 0f else (size.width * position.first) / 100
      val endX: Float = min(size.width, size.width * position.second / 100)

      drawLine(
        color,
        start = Offset(startX, size.height - height.toPx() / 2),
        end = Offset(endX, size.height - height.toPx() / 2),
        strokeWidth = height.toPx()
      )
    }
    val markerCenter = size.width * markerPosition / 100
    val path = Path().apply {
      moveTo(markerCenter, 0f)
      lineTo(markerCenter - height.toPx() / 2, height.toPx() / 2)
      lineTo(markerCenter, height.toPx())
      lineTo(markerCenter + height.toPx() / 2, height.toPx() / 2)
      close()
    }
    drawPath(path, Color.White)
  }
}

@Preview
@Composable
private fun HeartRateZonesPreview() {
  val lines = HeartRateZones().getHeartRateZonesColors()
  HeartRatesZonesRoot(markerPosition = 70, lines = lines)
}

@Preview
@Composable
private fun HeartRateTargetZonesPreview() {
  val lines = HeartRateZones().getTargetZonesColor(listOf(2, 3))
  HeartRatesZonesRoot(markerPosition = 70, lines = lines)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HeartRateZonesWithTargetPreview() {
  GarminPhoneActivityTheme {
    val lines = HeartRateZones().getHeartRateZonesColors()
    val targetLines = HeartRateZones().getTargetZonesColor(listOf(2, 3))
    val markerPosition = 80

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
      HeartRatesZonesRoot(markerPosition = markerPosition, lines = targetLines)
      HeartRatesZonesRoot(markerPosition = markerPosition, lines = lines)
    }
  }
}