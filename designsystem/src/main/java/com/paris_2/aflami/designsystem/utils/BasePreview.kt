package com.paris_2.aflami.designsystem.utils
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun BasePreview( modifier: Modifier = Modifier , content: @Composable () -> Unit) {
    AflamiTheme {
        Surface(modifier = modifier,color = Theme.colors.surface) {
            content()
        }
    }
}