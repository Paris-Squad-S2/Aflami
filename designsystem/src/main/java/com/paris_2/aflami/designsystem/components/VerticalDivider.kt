package com.paris_2.aflami.designsystem.components

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
fun VerticalDivider (
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
){
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}