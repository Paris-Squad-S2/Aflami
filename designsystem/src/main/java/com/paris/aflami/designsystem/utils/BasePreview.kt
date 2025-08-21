package com.paris.aflami.designsystem.utils
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun BasePreview( modifier: Modifier = Modifier , content: @Composable () -> Unit) {
    AflamiTheme {
        Surface(modifier = modifier,color = Theme.colors.surface) {
            content()
        }
    }
}