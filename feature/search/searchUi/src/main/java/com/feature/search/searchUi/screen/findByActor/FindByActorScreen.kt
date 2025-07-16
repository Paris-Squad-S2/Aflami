package com.feature.search.searchUi.screen.findByActor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.search.searchUi.R
import com.feature.search.searchUi.comon.components.SearchResultContent
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TextField
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun FindByActorScreen(viewModel: FindByActorViewModel = koinViewModel()) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    FindByActorScreenContent(
        state = screenState.value,
        findByActorScreenInteractionListener = viewModel,
    )
}

@Composable
fun FindByActorScreenContent(
    state: FindByActorScreenState,
    findByActorScreenInteractionListener: FindByActorScreenInteractionListener
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
    ) {
        TopAppBar(
            modifier = Modifier
                .statusBarsPadding(),
            title = stringResource(R.string.find_by_actor),
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = findByActorScreenInteractionListener::onNavigateBack,
                )
            ),
        )
        TextField(
            value = state.uiState.searchQuery,
            onValueChange = findByActorScreenInteractionListener::onSearchQueryChange,
            placeholder = stringResource(R.string.search),
        )
        if (state.uiState.searchQuery.isEmpty()) {
            PlaceholderView(
                modifier = Modifier
                    .fillMaxSize(),
                image = painterResource(RDesignSystem.drawable.img_find_by_actor),
                title = stringResource(R.string.find_by_actor),
                subTitle = stringResource(R.string.start_exploring_your_favorite_actor_s_movies),
                spacer = 16.dp
            )
        } else if (state.errorMessage != null||state.uiState.searchResult.collectAsLazyPagingItems().loadState.hasError) {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = findByActorScreenInteractionListener::onRetrySearchQuery
            )
        } else if (state.uiState.searchResult.collectAsLazyPagingItems().loadState.refresh == LoadState.Loading ) {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.uiState.searchResult.collectAsLazyPagingItems().itemCount==0) {
            PlaceholderView(
                modifier = Modifier.fillMaxSize(),
                image = painterResource(RDesignSystem.drawable.img_no_search_result),
                title = stringResource(R.string.no_search_result),
                subTitle = stringResource(R.string.please_try_with_another_keyword),
                spacer = 16.dp
            )
        } else {
            SearchResultContent(
                searchResult = state.uiState.searchResult.collectAsLazyPagingItems(),
                onMediaCardClick = findByActorScreenInteractionListener::onMediaCardClick
            )
        }
    }
}