package com.github.basva923.garminphoneactivity.performancemonitor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = AppRed,
  onPrimary = AppWhite,
  secondary = AppGreen,
  tertiary = AppYellow,
  onSecondary = AppGreen,
  background = AppBlack,
  onBackground = AppWhite,
)


@Composable
fun GarminPhoneActivityTheme(
  content: @Composable () -> Unit
) {
  val colorScheme =  DarkColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}