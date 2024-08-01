package com.github.basva923.garminphoneactivity.performancemonitor.shared

interface AppError

sealed interface AppResult<D, E: AppError> {
  data class Success<D, E: AppError>(val data: D): AppResult<D, E>
  data class Error<D, E: AppError>(val error: E): AppResult<D, E>
}

sealed interface DevError: AppError {
  data object NotImplemented: DevError
}
