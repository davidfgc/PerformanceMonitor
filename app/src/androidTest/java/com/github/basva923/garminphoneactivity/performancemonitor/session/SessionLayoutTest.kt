package com.github.basva923.garminphoneactivity.performancemonitor.session

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class SessionLayoutTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun test() {
        val heartRate = 70
        val time = 120
        val sessionData = object : SessionData {
            override val heartRate: Int = heartRate
            override val heartRateZone: Int = 3
            override val time: Int = 120
            override val cadence: Int = 50
            override val speed: Float = 10f
            override val distance: Float = 100f
            override val latitude: Double = 0.0
            override val longitude: Double = 0.0
        }
        composeRule.setContent {
            SessionLayout(sessionData, emptyList())
        }

        composeRule.onNodeWithText("HR: $heartRate").assertExists()
        composeRule.onNodeWithText("Time: $time").assertExists()
    }
}