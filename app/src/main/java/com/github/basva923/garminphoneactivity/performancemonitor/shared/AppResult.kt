package com.github.basva923.garminphoneactivity.performancemonitor.shared

sealed interface AppResult<D, E> {
  data class Success<D, E>(val data: D): AppResult<D, E>
  data class Error<D, E>(val error: E): AppResult<D, E>
}
