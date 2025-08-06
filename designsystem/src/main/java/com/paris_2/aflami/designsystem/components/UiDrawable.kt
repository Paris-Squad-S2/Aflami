package com.paris_2.aflami.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter

sealed class UiDrawable {
    data class Resource(@DrawableRes val resId: Int) : UiDrawable()
    data class Custom(val painter: Painter) : UiDrawable()
}

@Composable
fun UiDrawable?.asPainter(): Painter? {
    return when (this) {
        is UiDrawable.Resource -> painterResource(id = resId)
        is UiDrawable.Custom -> painter
        else -> null
    }
}
