package com.github.basva923.garminphoneactivity.performancemonitor.settings

import com.github.basva923.garminphoneactivity.performancemonitor.shared.AppResult

interface SettingsRepository {
  fun getHeartRateTargetZones(): IntRange
  interface SettingsLocalRepository {
    fun getHeartRateTargetZones(): AppResult<IntRange, String>
  }
}

class SettingsRepositoryImpl(
  private val localRepository: SettingsRepository.SettingsLocalRepository = SettingsLocalRepositoryImpl()
): SettingsRepository {
  private val defaultTargetZones = 2..3

  override fun getHeartRateTargetZones(): IntRange {
    return when (val res = localRepository.getHeartRateTargetZones()) {
      is AppResult.Success -> res.data
      is AppResult.Error -> defaultTargetZones
    }
  }
}

class SettingsLocalRepositoryImpl: SettingsRepository.SettingsLocalRepository {
  override fun getHeartRateTargetZones(): AppResult<IntRange, String> {
    // TODO get from shared preferences
    return AppResult.Error("Not implemented")
  }
}