package com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.domain.HeartRateZone
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppGreen
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppRed
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.GarminPhoneActivityTheme
import kotlin.math.min

@Composable
fun HeartRateZones(
  markerPosition: Int,
  modifier: Modifier = Modifier,
  heartRateZones: List<HeartRateZone> = HeartRateZone.entries,
  height: Dp = 10.dp
) {
  val zones = heartRateZones.map { Zone(it.percentageRange, it.asColor()) }
  LinearZones(markerPosition, zones, modifier, height)
}

@Composable
fun LinearZones(markerPosition: Int, zones: List<Zone>, modifier: Modifier = Modifier, height: Dp = 10.dp) {
  Canvas(modifier = modifier
    .fillMaxWidth()
    .height(height)) {
    zones.forEachIndexed{ index, it ->
      val startX: Float = if (index == 0) 0f else (size.width * it.range.first) / 100
      val endX: Float = min(size.width, size.width * it.range.last / 100)

      drawLine(
        color = it.color,
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

data class Zone(val range: IntRange, val color: Color)

@Composable
fun HeartRateTargetZones(
  markerPosition: Int,
  targetZones: List<HeartRateZone>,
  modifier: Modifier = Modifier,
  heartRateZones: List<HeartRateZone> = HeartRateZone.entries,
  height: Dp = 10.dp
) {
  val zones = heartRateZones.map {
    val color = if (targetZones.contains(it)) AppGreen
    else AppRed

    Zone(it.percentageRange, color)
  }
  LinearZones(markerPosition, zones, modifier, height)
}

@Preview
@Composable
private fun HeartRateZonesPreview() {
  HeartRateZones(75)
}

@Preview
@Composable
private fun HeartRateTargetZonesPreview() {
  val targetZones = listOf(HeartRateZone.AEROBIC, HeartRateZone.THRESHOLD)
  HeartRateTargetZones(75, targetZones)
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun HeartRateZonesWithTargetPreview() {
  GarminPhoneActivityTheme {
    val targetZones = listOf(HeartRateZone.AEROBIC, HeartRateZone.THRESHOLD)
    val markerPosition = 75

    Column(Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
      HeartRateZones(markerPosition)
      HeartRateTargetZones(markerPosition, targetZones)
    }
  }
}