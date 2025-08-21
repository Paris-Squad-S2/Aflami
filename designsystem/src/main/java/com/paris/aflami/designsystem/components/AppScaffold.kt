package com.paris.aflami.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun AppScaffold(
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