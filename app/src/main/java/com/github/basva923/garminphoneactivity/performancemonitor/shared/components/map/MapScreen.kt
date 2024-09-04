package com.github.basva923.garminphoneactivity.performancemonitor.shared.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            bearing(0.0)
            center(Point.fromLngLat(-98.0, 39.5))
            pitch(0.0)
            zoom(12.0)
        }
    }

    MapLayout(mapViewportState, modifier = modifier.fillMaxSize())
}

@Composable
fun MapLayout(mapViewportState: MapViewportState, modifier: Modifier = Modifier) {

    MapboxMap(
        modifier,
        mapViewportState = mapViewportState
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun MapLayoutPreview() {
//    val mapViewportState = rememberMapViewportState {
//        setCameraOptions {
//            bearing(0.0)
//            center(Point.fromLngLat(-98.0, 39.5))
//            pitch(0.0)
//            zoom(2.0)
//        }
//    }
//
//    MapLayout(mapViewportState, modifier = Modifier)
//}
