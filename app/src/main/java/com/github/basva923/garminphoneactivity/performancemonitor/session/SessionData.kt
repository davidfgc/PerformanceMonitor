package com.github.basva923.garminphoneactivity.performancemonitor.session

interface SessionData {
  val heartRate: Int
  val heartRateZone: Int
  val time: Int
  val altitude: Int
}

class EmptySessionData: SessionData {
  override val heartRate: Int = 0
  override val heartRateZone: Int = 0
  override val time: Int = 0
  override val altitude: Int = 0
}