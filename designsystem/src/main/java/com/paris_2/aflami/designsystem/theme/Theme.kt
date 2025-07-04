package com.paris_2.aflami.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.paris_2.aflami.designsystem.color.Colors
import com.paris_2.aflami.designsystem.color.LocalColors
import com.paris_2.aflami.designsystem.text_style.AflamiTextStyle
import com.paris_2.aflami.designsystem.text_style.LocalAflamiTextStyle

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