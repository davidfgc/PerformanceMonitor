package com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.ui

import com.github.basva923.garminphoneactivity.performancemonitor.heartratezones.domain.HeartRateZone
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppBlue
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppGray
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppGreen
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppRed
import com.github.basva923.garminphoneactivity.performancemonitor.ui.theme.AppYellow

fun HeartRateZone.asColor() = when (this.color) {
  HeartRateZone.Color.GRAY -> AppGray
  HeartRateZone.Color.BLUE -> AppBlue
  HeartRateZone.Color.GREEN -> AppGreen
  HeartRateZone.Color.YELLOW -> AppYellow
  HeartRateZone.Color.RED -> AppRed
}