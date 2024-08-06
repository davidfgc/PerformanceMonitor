package com.github.basva923.garminphoneactivity.performancemonitor.session

interface SessionData {
  val heartRate: Int
  val heartRateZone: Int
  val time: Int
  val cadence: Int
  val speed: Float
  val distance: Float
  val latitude: Double
  val longitude: Double
}

class EmptySessionData: SessionData {
  override val heartRate: Int = 0
  override val heartRateZone: Int = 0
  override val time: Int = 0
  override val cadence: Int = 0
  override val speed: Float = 0f
  override val distance: Float = 0f
  override val latitude: Double = 0.0
  override val longitude: Double = 0.0
}