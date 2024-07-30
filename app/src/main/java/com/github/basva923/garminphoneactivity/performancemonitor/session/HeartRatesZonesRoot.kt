package com.github.basva923.garminphoneactivity.performancemonitor.session

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.basva923.garminphoneactivity.model.LiveTrackInfo
import com.github.basva923.garminphoneactivity.model.Track

@Composable
fun HeartRatesZonesRoot(positionMarker: Int, modifier: Modifier = Modifier) {
  val colors = listOf(
    Color.LightGray,
    Color.Blue,
    Color.Green,
    Color.Yellow,
    Color.Red
  )
  val zonesPercents = LiveTrackInfo(Track()).hrZoneFinder.zonesPercents
  val lines = zonesPercents.mapIndexed { index, it ->
    colors[index] to it
  }
  HeartRateZones(positionMarker, lines = lines, modifier)
}

//fun DrawScope.HeartRateZones(markerPos: Int, lines: List<Pair<Color, Pair<Int, Int>>>, modifier: Modifier = Modifier, height: Dp = 10.dp) {
@Composable
fun HeartRateZones(markerPos: Int, lines: List<Pair<Color, Pair<Int, Int>>>, modifier: Modifier = Modifier, height: Dp = 10.dp) {
  Canvas(modifier = modifier
    .fillMaxSize()
  ) {
    lines.forEach { (color, position) ->
      drawLine(
        color,
        start = Offset((size.width * position.first) / 100, size.height - height.toPx() / 2),
        end = Offset(size.width * position.second / 100, size.height - height.toPx() / 2),
        strokeWidth = height.toPx()
      )
      drawCircle(Color.Black, radius = height.toPx(), center = Offset(size.width * markerPos / 100, size.height - height.toPx() / 2))
    }
  }
}