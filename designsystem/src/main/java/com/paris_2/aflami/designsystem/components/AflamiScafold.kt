package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun AflamiScafold(
    bottomBar: @Composable () -> Unit = { },
    content: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = bottomBar,
        containerColor = Theme.colors.surface,
        contentColor = Theme.colors.onPrimaryColors.onPrimary,
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}

@Composable
@PreviewMultiDevices
fun AflamiScafoldPreview() {
    AflamiScafold(
        bottomBar = { },
        content = { }
    )
}