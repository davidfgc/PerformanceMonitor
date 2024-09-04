package com.github.basva923.garminphoneactivity.performancemonitor.shared.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapScreenViewModel = viewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.initialize(context)
    }
    val state by viewModel.mapScreenState.collectAsState()
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            bearing(0.0)
            center(state.location)
            pitch(0.0)
            zoom(18.0)
        }
    }
    LaunchedEffect(key1 = state.location) {
        mapViewportState.easeTo(cameraOptions { center(state.location) })
    }

    MapLayout(mapViewportState, modifier = modifier.fillMaxSize())
}

@Composable
fun MapLayout(mapViewportState: MapViewportState, modifier: Modifier = Modifier) {
    MapboxMap(
        modifier,
        mapViewportState = mapViewportState,
    )
}
