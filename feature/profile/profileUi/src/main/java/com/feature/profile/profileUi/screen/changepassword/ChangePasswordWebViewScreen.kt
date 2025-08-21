package com.feature.profile.profileUi.screen.changepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.profile.profileUi.screen.components.WebViewComposable
import com.paris.aflami.designsystem.components.NetworkError
import com.paris.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun ChangePasswordWebViewScreen(viewModel: ChangePasswordViewModel = hiltViewModel()) {

    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    ChangePasswordScreenContent(uiState.value)
}

@Composable
private fun ChangePasswordScreenContent(
    uIState: ChangePasswordUiState
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Theme.colors.primary.copy(alpha = 0.24f),
                        Theme.colors.primary.copy(alpha = 0f),
                    )
                )
            )
            .statusBarsPadding()
    ) {
        WebViewComposable(
            url = uIState.resetPasswordUrl,
            modifier = Modifier.fillMaxSize(),
            loadingPlaceholder = {
                PageLoadingPlaceHolder(
                    modifier = Modifier.fillMaxSize()
                )
            },
            errorPlaceholder = { onRetry ->
                NetworkError(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.surface),
                    onRetry = onRetry,
                )
            }
        )
    }
}