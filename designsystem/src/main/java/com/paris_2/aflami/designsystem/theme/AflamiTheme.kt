package com.paris_2.aflami.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.paris_2.aflami.designsystem.color.LocalColors
import com.paris_2.aflami.designsystem.color.darkThemeColors
import com.paris_2.aflami.designsystem.color.lightThemeColors
import com.paris_2.aflami.designsystem.text_style.LocalAflamiTextStyle
import com.paris_2.aflami.designsystem.text_style.generateAflamiTextStyle

@Composable
fun AflamiTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colors = if (isDarkTheme) darkThemeColors else lightThemeColors
    val coloredTextStyle = remember(colors) { generateAflamiTextStyle(colors) }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalAflamiTextStyle provides coloredTextStyle
    ){
        content()
    }
}