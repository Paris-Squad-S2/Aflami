package com.paris_2.aflami.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.aflami.designsystem.color.LocalColors
import com.paris_2.aflami.designsystem.color.darkThemeColors
import com.paris_2.aflami.designsystem.color.lightThemeColors
import com.paris_2.aflami.designsystem.text_style.LocalAflamiTextStyle
import com.paris_2.aflami.designsystem.text_style.generateAflamiTextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun AflamiTheme(
    isDarkTheme: Flow<Boolean> = flowOf(true),
    content: @Composable () -> Unit
){

    val isInDarkTheme = isDarkTheme.collectAsStateWithLifecycle(initialValue = true)
    val colors = if (isInDarkTheme.value) darkThemeColors else lightThemeColors
    val coloredTextStyle = remember(colors) { generateAflamiTextStyle(colors) }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalAflamiTextStyle provides coloredTextStyle
    ){
        content()
    }
}