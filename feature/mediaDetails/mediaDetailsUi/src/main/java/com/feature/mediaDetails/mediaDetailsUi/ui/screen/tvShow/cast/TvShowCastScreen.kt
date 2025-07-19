package com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.castSection.CastItem
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun TvShowCastScreen(
    viewModel: TvShowCastViewModel = koinViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    TvShowCastScreenContent(state = state.value, tvShowCastScreenInteractionListener = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowCastScreenContent(
    state: TvShowCastUiState,
    tvShowCastScreenInteractionListener: TvShowCastScreenInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
    ) {
        TopAppBar(
            modifier = Modifier.statusBarsPadding(),
            title = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.cast),
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = tvShowCastScreenInteractionListener::onNavigateBack
                )
            )
        )

        when {
            state.errorMessage != null -> {
                NetworkError(
                    modifier = Modifier.fillMaxSize(),
                    onRetry = tvShowCastScreenInteractionListener::onRetryLoadCast
                )
            }

            state.isLoading -> {
                PageLoadingPlaceHolder(
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.cast.isEmpty() -> {
                PlaceholderView(
                    modifier = Modifier.fillMaxSize(),
                    image = painterResource(RDesignSystem.drawable.ic_network_error),
                    title = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.no_cast_available),
                    subTitle = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.cast_information_not_available),
                    spacer = 16.dp
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.cast.chunked(3)) { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItems.forEach { cast ->
                                CastItem(
                                    imageUrl = cast.imageUrl,
                                    name = cast.name,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}