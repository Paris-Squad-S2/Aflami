package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.aflami.designsystem.components.AflamiText
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModelViewModel = koinViewModel()) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AflamiText(
            text = state.value.uiState,
            modifier = Modifier.clickable {
                viewModel.onNavigate()
            }
        )
    }
}
