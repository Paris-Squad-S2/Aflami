package com.feature.mediaDetails.mediaDetailsUi.ui.screen.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.video.components.WebViewComposable
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VideoWebViewScreen(viewModel: VideoWebViewViewModel = koinViewModel()) {

    val uiState = viewModel.screenState.collectAsStateWithLifecycle()
    MovieVideoScreenContent(uiState.value, viewModel)
}

@Composable
private fun MovieVideoScreenContent(
    uIState: VideoWebUIState,
    viewViewModel: VideoWebViewViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        WebViewComposable(
            url = uIState.videoUrl,
            onWebMessageReceived = { message: String ->
                viewViewModel.onNavigateBack()
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
        )
    }

}