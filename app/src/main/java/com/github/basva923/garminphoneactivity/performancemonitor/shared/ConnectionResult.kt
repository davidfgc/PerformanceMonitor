package com.github.basva923.garminphoneactivity.performancemonitor.shared

sealed class ConnectionResult {
  data object Success: ConnectionResult()
  data class Error(val message: String): ConnectionResult()
}