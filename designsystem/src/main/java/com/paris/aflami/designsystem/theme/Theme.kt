package com.paris.aflami.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.paris.aflami.designsystem.color.Colors
import com.paris.aflami.designsystem.color.LocalColors
import com.paris.aflami.designsystem.text_style.AflamiTextStyle
import com.paris.aflami.designsystem.text_style.LocalAflamiTextStyle

object Theme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val textStyle: AflamiTextStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalAflamiTextStyle.current
}