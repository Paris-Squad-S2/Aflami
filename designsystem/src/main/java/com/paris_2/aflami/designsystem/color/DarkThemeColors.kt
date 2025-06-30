package com.paris_2.aflami.designsystem.color

import androidx.compose.ui.graphics.Color

val darkThemeColors = Colors(
    primary = Color(0xFFD85895),
    secondary = Color(0xFF64163B),
    primaryVariant = Color(0xFF1F1218),
    text = TextColors(
        title = Color(0xDEFFFFFF),
        body = Color(0x99FFFFFF),
        hint = Color(0x61FFFFFF)
    ),
    stroke = Color(0x14FFFFFF),
    surface = Color(0xFF0D090B),
    surfaceHigh = Color(0xFF141112),
    onPrimaryColors = OnPrimaryColors(
        onPrimary = Color(0xDEFFFFFF),
        onPrimaryBody = Color(0x80FFFFFF),
        onPrimaryHint = Color(0x14FFFFFF),
        onPrimaryButton = Color(0xFFFFFFFF).copy(alpha = 0.08f)
    ),
    disable = Color(0xFF292828),
    iconBackground = Color(0xFF0D090B),
    blurOverly = Color(0x80000000),
    gradient = Gradient(
        overly = listOf(Color(0xFF0D090B), Color(0x000D090B)),
        streakGradient = listOf(Color(0x1FFFFFFF), Color(0x80FFFFFF)),
        pointsOverly = listOf(Color(0xFF3B0D23), Color(0xFF7D1C4A)),
        blueGradient = listOf(Color(0xFF53ABF9), Color(0xFF336490)),
        pinkGradient = listOf(Color(0xFFD85895), Color(0xFF803559))
    ),
    status = Status(
        redAccent = Color(0xFFA63A42),
        redVariant = Color(0xFF1F1213),
        yellowAccent = Color(0xFFE5A02E),
        greenAccent = Color(0xFF3D8C40),
        greenVariant = Color(0xFF0C140D),
        darkBlue = Color(0xFF0C57C8),
        blueAccent = Color(0xFF2BA3D9),
        blueCard = Color(0xFF121B1F),
        navyCard = Color(0xFF12151F),
        yellowCard = Color(0xFF1F1A12),
        backgroundCircles = Color(0x14FFFFFF),
        profileOverlay = Color(0x800D090B)
    )
)