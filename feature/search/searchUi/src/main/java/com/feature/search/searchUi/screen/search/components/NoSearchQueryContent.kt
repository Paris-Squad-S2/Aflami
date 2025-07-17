package com.feature.search.searchUi.screen.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.search.searchUi.R
import com.feature.search.searchUi.screen.search.SearchScreenInteractionListener
import com.feature.search.searchUi.screen.search.SearchScreenState
import com.feature.search.searchUi.screen.search.SearchTypeUi
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.AflamiHorizontalDivider
import com.paris_2.aflami.designsystem.components.AflamiText
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.RecentSearchItem
import com.paris_2.aflami.designsystem.components.SearchSuggestionHub
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun NoSearchQueryContent(
    state: SearchScreenState,
    searchScreenInteractionListener: SearchScreenInteractionListener
) {
    LazyColumn {
        item {
            AflamiText(
                text = stringResource(R.string.search_suggestions_hub),
                style = Theme.textStyle.title.medium,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
            )
        }
        item {
            Row(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchSuggestionHub(
                    title = stringResource(R.string.world_tour),
                    description = stringResource(R.string.explore_world_cinema),
                    icon = painterResource(id = com.paris_2.aflami.designsystem.R.drawable.img_world_tour),
                    gradientColors = Theme.colors.gradient.pinkGradient,
                    onCardClick = searchScreenInteractionListener::onNavigateToWorldTourScreen,
                    modifier = Modifier.weight(1f)
                )
                SearchSuggestionHub(
                    title = stringResource(R.string.find_by_actor),
                    description = stringResource(R.string.search_by_favorite_actor),
                    icon = painterResource(id = com.paris_2.aflami.designsystem.R.drawable.img_find_by_actor),
                    gradientColors = Theme.colors.gradient.blueGradient,
                    onCardClick = searchScreenInteractionListener::onNavigateToFindByActorScreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            AnimatedVisibility(
                visible = state.searchUiState.recentSearches.isNotEmpty(),
                enter = androidx.compose.animation.expandVertically(),
                exit = androidx.compose.animation.shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 12.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AflamiText(
                        text = stringResource(R.string.recent_searches),
                        style = Theme.textStyle.title.medium,
                        color = Theme.colors.text.title,
                    )
                    AflamiButton(
                        onClick = searchScreenInteractionListener::onClearAllRecentSearches,
                        text = R.string.clear_all,
                        type = ButtonType.TextButton,
                        state = ButtonState.Normal
                    )
                }
            }
        }
        if (state.errorMessage != null) {
            item {
                NetworkError(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 70.dp),
                    onRetry = searchScreenInteractionListener::onRetryRecentSearches
                ) //TODO What the Screen of the error, it's not network only here
            }
        } else if (state.isLoading) {
            item {
                PageLoadingPlaceHolder(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 70.dp)

                )
            }
        } else if (state.searchUiState.recentSearches.isEmpty()) {
            item {
                PlaceholderView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 70.dp),
                    image = painterResource(com.paris_2.aflami.designsystem.R.drawable.img_no_search_result),
                    subTitle = stringResource(R.string.start_exploring_search_for_your_favorite_movies_series_and_shows),
                    spacer = 16.dp
                )
            }
        } else {
            items(
                count = state.searchUiState.recentSearches.size,
                key = { index -> state.searchUiState.recentSearches[index].searchTitle + state.searchUiState.recentSearches[index].searchType.name }
            ) { index ->
                val recentSearch = state.searchUiState.recentSearches[index]
                RecentSearchItem(
                    modifier = Modifier.animateItem(),
                    recentSearchTitle = recentSearch.searchTitle + if (recentSearch.searchType!= SearchTypeUi.Query) " (${stringResource(recentSearch.searchType.displayNameResId)})" else "",
                    onRecentSearchClick = {
                        searchScreenInteractionListener.onRecentSearchClick(
                            recentSearch.searchTitle,
                            recentSearch.searchType
                        )
                    },
                    onDeleteRecentSearch = {
                        searchScreenInteractionListener.onClearRecentSearch(
                            recentSearch.searchTitle,
                            recentSearch.searchType
                        )
                        searchScreenInteractionListener.onRetryRecentSearches()
                    }
                )
                if (index < state.searchUiState.recentSearches.size - 1) {
                    AflamiHorizontalDivider(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                        color = Theme.colors.stroke
                    )
                }
            }
        }
    }
}