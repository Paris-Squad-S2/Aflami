package com.paris_2.aflami.designsystem.theme

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
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
    isDarkTheme: Flow<Boolean> = flowOf(false),
    isLightStatusBars : Boolean? = null,
    content: @Composable () -> Unit
){

    val isInDarkTheme = isDarkTheme.collectAsStateWithLifecycle(initialValue = false)
    val colors = if (isInDarkTheme.value) darkThemeColors else lightThemeColors
    val coloredTextStyle = remember(colors) { generateAflamiTextStyle(colors) }

    val context = LocalContext.current
    val view = LocalView.current
    val activity = context as? ComponentActivity

    LaunchedEffect(isInDarkTheme.value) {
        activity?.window?.also { window ->
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = isLightStatusBars ?: !isInDarkTheme.value
                isAppearanceLightNavigationBars = isLightStatusBars ?: !isInDarkTheme.value
            }
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalAflamiTextStyle provides coloredTextStyle
    ){
        content()
    }
}