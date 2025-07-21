package com.feature.authentication.authenticationUi.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.authentication.authenticationUi.screen.register.components.WebViewComposable
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterWebViewScreen(viewModel: RegisterViewModel = koinViewModel()) {

    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    RegisterScreenContent(uiState.value, viewModel)
}

@Composable
private fun RegisterScreenContent(uIState: RegisterUIState, registerViewModel: RegisterViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFD85895).copy(alpha = 0.24f),
                        Color(0xFFD85895).copy(alpha = 0f)
                    )
                )
            )
            .statusBarsPadding()
    ) {
        WebViewComposable(
            url = uIState.registrationUrl,
            onWebMessageReceived = { message: String ->
                if (message == "registration_complete") {
                    registerViewModel.onNavigateBack()
                }
            },
            modifier = Modifier.fillMaxSize(),
            loadingPlaceholder = {
                PageLoadingPlaceHolder(
                    modifier = Modifier.fillMaxSize()
                )
            },
            errorPlaceholder = { onRetry ->
                NetworkError(
                    modifier = Modifier.fillMaxSize()
                        .background(Theme.colors.surface),
                    onRetry = onRetry,
                )
            },
        )
    }

}