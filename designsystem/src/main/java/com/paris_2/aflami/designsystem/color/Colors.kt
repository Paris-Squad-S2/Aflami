package com.paris_2.aflami.designsystem.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class Colors(
    val primary: Color,
    val secondary: Color,
    val primaryVariant: Color,
    val text: TextColors,
    val stroke: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val onPrimaryColors: OnPrimaryColors,
    val disable: Color,
    val iconBackground: Color,
    val blurOverly: Color,
    val gradient: Gradient,
    val status: Status
)

data class TextColors(
    val title: Color,
    val body: Color,
    val hint: Color,
)

data class Status(
    val redAccent: Color,
    val redVariant: Color,
    val yellowAccent: Color,
    val greenAccent: Color,
    val greenVariant: Color,
    val darkBlue: Color,
    val blueAccent: Color,
    val blueCard: Color,
    val navyCard: Color,
    val yellowCard: Color,
    val backgroundCircles: Color,
    val profileOverlay: Color
)

data class OnPrimaryColors(
    val onPrimary: Color,
    val onPrimaryBody: Color,
    val onPrimaryHint: Color,
    val onPrimaryButton: Color
)

data class Gradient(
    val overly: List<Color>,
    val streakGradient: List<Color>,
    val pointsOverly: List<Color>,
    val blueGradient : List<Color>,
    val pinkGradient : List<Color>
)

val LocalColors = staticCompositionLocalOf { lightThemeColors }
