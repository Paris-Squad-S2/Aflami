package com.feature.search.searchUi.screen.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.search.searchUi.R
import com.feature.search.searchUi.comon.components.SearchResultContent
import com.feature.search.searchUi.screen.search.SearchScreenInteractionListener
import com.feature.search.searchUi.screen.search.SearchScreenState
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.TabRow

@Composable
fun WithSearchQueryContent(
    state: SearchScreenState,
    searchScreenInteractionListener: SearchScreenInteractionListener,
) {
    val tabs = listOf(
        stringResource(R.string.movies),
        stringResource(R.string.tv_shows)
    )
    if (state.errorMessage != null
        || state.searchUiState.filteredMoviesResult.collectAsLazyPagingItems().loadState.hasError
        || state.searchUiState.filteredTvShowsResult.collectAsLazyPagingItems().loadState.hasError) {
        NetworkError(
            modifier = Modifier.fillMaxSize(),
            onRetry = searchScreenInteractionListener::onRetrySearchQuery
        )//TODO: Ask if it always will be a network error or how to handle other types of errors
    } else if (state.isLoading) {
        PageLoadingPlaceHolder(
            modifier = Modifier.fillMaxSize()
        )
    } else if (state.searchUiState.filteredMoviesResult.collectAsLazyPagingItems().loadState.refresh == LoadState.Loading
        && state.searchUiState.filteredTvShowsResult.collectAsLazyPagingItems().loadState.refresh == LoadState.Loading) {
        PageLoadingPlaceHolder(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Column {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                onTabSelected = searchScreenInteractionListener::onSelectTab,
                selectedIndex = state.searchUiState.selectedTabIndex,
                tabItems = tabs
            )
            val searchResult =
                if (state.searchUiState.selectedTabIndex == 0) state.searchUiState.filteredMoviesResult else state.searchUiState.filteredTvShowsResult
            if (searchResult.collectAsLazyPagingItems().itemCount == 0) {
                PlaceholderView(
                    modifier = Modifier.fillMaxSize(),
                    image = painterResource(com.paris_2.aflami.designsystem.R.drawable.img_no_search_result),
                    title = stringResource(R.string.no_search_result),
                    subTitle = stringResource(R.string.please_try_with_another_keyword),
                    spacer = 16.dp
                )
            } else {
                SearchResultContent(
                    searchResult = searchResult.collectAsLazyPagingItems(),
                    onMediaCardClick = searchScreenInteractionListener::onMediaCardClick
                )
            }
        }
    }
}