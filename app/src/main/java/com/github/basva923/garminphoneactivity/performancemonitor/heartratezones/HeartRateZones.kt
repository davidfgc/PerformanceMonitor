package com.github.basva923.garminphoneactivity.performancemonitor.heartratezones

import androidx.compose.ui.graphics.Color

class HeartRateZones(colors: List<Color> = emptyList(), zones: Array<Pair<Int, Int>> = emptyArray()) {

  private val inTargetColor = Color(0xFF228B22)
  private val onEdgeColor = Color(0xFF808000)
  private val outOfTargetColor = Color(0xFFDC3B61)

  private val defaultColors = listOf(
    Color.DarkGray,
    Color.Blue,
    inTargetColor,
    onEdgeColor,
    outOfTargetColor
  )
  private val defaultZones = arrayOf(
    Pair(50, 60),
    Pair(60, 70),
    Pair(70, 80),
    Pair(80, 90),
    Pair(90, 100)
  )

  private var colors: List<Color> = colors.ifEmpty { defaultColors }
  private val zones: Array<Pair<Int, Int>> = zones.ifEmpty { defaultZones }

  fun getHeartRateZonesColors(): List<Pair<Pair<Int, Int>, Color>> {
//    val zonesPercents = LiveTrackInfo(Track()).hrZoneFinder.zonesPercents

    return zones.mapIndexed { index, it ->
      it to colors[index]
    }
  }

  fun getTargetZonesColor(targetZones: List<Int>): List<Pair<Pair<Int, Int>, Color>> {

    return zones.mapIndexed { index, it ->
      if (targetZones.contains(index + 1)) it to inTargetColor
      else it to outOfTargetColor
    }
  }

}