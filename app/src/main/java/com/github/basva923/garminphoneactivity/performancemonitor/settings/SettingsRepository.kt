package com.github.basva923.garminphoneactivity.performancemonitor.settings

import com.github.basva923.garminphoneactivity.performancemonitor.shared.SettingsResult

interface SettingsRepository {
  fun getHeartRateTargetZones(): IntRange
  interface SettingsLocalRepository {
    fun getHeartRateTargetZones(): SettingsResult
  }
}

class SettingsRepositoryImpl(
  private val localRepository: SettingsRepository.SettingsLocalRepository = SettingsLocalRepositoryImpl()
): SettingsRepository {
  private val defaultTargetZones = 2..3

  override fun getHeartRateTargetZones(): IntRange {
    return when (val res = localRepository.getHeartRateTargetZones()) {
      is SettingsResult.Success -> res.data
      is SettingsResult.Error -> defaultTargetZones
    }
  }
}

class SettingsLocalRepositoryImpl: SettingsRepository.SettingsLocalRepository {
  override fun getHeartRateTargetZones(): SettingsResult {
    // TODO get from shared preferences
    return SettingsResult.Error("Not implemented")
  }
}