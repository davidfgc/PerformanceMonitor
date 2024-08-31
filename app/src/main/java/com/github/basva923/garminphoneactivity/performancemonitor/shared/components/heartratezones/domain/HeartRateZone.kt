package com.github.basva923.garminphoneactivity.performancemonitor.shared.components.heartratezones.domain

enum class HeartRateZone(val percentageRange: IntRange, val color: Color) {
  WARM_UP(IntRange(50, 60), Color.GRAY),
  EASY(IntRange(60, 70), Color.BLUE),
  AEROBIC(IntRange(70, 80), Color.GREEN),
  THRESHOLD(IntRange(80, 90), Color.YELLOW),
  MAXIMUM(IntRange(90, 100), Color.RED);

  enum class Color {
    GRAY,
    BLUE,
    GREEN,
    YELLOW,
    RED
  }
}

class UserHeartRate(private val maxHR: Int) {
  fun getPercentage(currentHR: Int) = currentHR * 100 / maxHR
}
