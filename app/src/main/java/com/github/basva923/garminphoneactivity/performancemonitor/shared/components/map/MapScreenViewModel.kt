package com.github.basva923.garminphoneactivity.performancemonitor.shared.components.map

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.basva923.garminphoneactivity.performancemonitor.boundaries.device.DeviceAdapter
import com.github.basva923.garminphoneactivity.performancemonitor.session.MapScreenState
import com.github.basva923.garminphoneactivity.performancemonitor.settings.SettingsRepository
import com.github.basva923.garminphoneactivity.performancemonitor.settings.SettingsRepositoryImpl
import com.github.basva923.garminphoneactivity.performancemonitor.shared.AppResult
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MapScreenViewModel(
    private val deviceAdapter: DeviceAdapter = DeviceAdapter(),
    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl()
): ViewModel() {

    private val _mapScreenState = MutableStateFlow(
        MapScreenState(Point.fromLngLat(0.0, 0.0)))
    val mapScreenState: StateFlow<MapScreenState> = _mapScreenState.asStateFlow()

    fun initialize(context: Context) {
        val useMocks = settingsRepository.getBuildConfig().useMocks
        deviceAdapter.initialize(context, useMocks) { initializeResult ->
            when (initializeResult) {
                is AppResult.Error -> TODO()
                is AppResult.Success -> {
                    deviceAdapter.observeDeviceData().onEach {
                        val point = Point.fromLngLat(it.longitude, it.latitude)
                        _mapScreenState.value = _mapScreenState.value.copy(location = point)
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}
