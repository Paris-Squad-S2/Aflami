package com.feature.authentication.authenticationUi.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.authentication.authenticationUi.comon.WebViewComposable
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun RegisterWebViewScreen(viewModel: RegisterViewModel = hiltViewModel()) {

    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    RegisterScreenContent(uiState.value, viewModel)
}

@Composable
private fun RegisterScreenContent(uiState: RegisterUIState, registerViewModel: RegisterViewModel) {

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
            url = uiState.registrationUrl,
            onWebMessageReceived = { message: String ->
                registerViewModel.onNavigateBack()
            },
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
            },
            onNavigationEvent = {
                registerViewModel.onNavigateBack()
            }
        )
    }

}