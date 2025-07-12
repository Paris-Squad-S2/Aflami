package com.feature.search.searchUi.screen.search.components

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
import com.domain.search.model.SearchType
import com.feature.search.searchUi.R
import com.feature.search.searchUi.screen.search.SearchScreenInteractionListener
import com.feature.search.searchUi.screen.search.SearchScreenState
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
                    icon = painterResource(id = com.paris_2.aflami.designsystem.R.drawable.img_world_tour),
                    gradientColors = Theme.colors.gradient.blueGradient,
                    onCardClick = searchScreenInteractionListener::onNavigateToFindByActorScreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
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
                if (state.uiState.recentSearches.isNotEmpty())
                    AflamiButton(
                        onClick = searchScreenInteractionListener::onClearAllRecentSearches,
                        text = R.string.clear_all,
                        type = ButtonType.TextButton,
                        state = ButtonState.Normal
                    )
            }
        }
        if (state.errorMessage != null) {
            item {
                NetworkError(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 30.dp),
                    onRetry = searchScreenInteractionListener::onRetryRecentSearches
                ) //TODO What the Screen of the error, it's not network only here
            }
        } else if (state.isLoading) {
            item {
                PageLoadingPlaceHolder(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 30.dp)

                )
            }
        } else if (state.uiState.recentSearches.isEmpty()) {
            item {
                PlaceholderView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 30.dp),
                    image = painterResource(com.paris_2.aflami.designsystem.R.drawable.img_no_search_result),
                    title = stringResource(R.string.no_search_history),
                    subTitle = stringResource(R.string.you_haven_t_searched_for_anything_yet),
                    spacer = 16.dp
                )
            }
        } else {
            items(
                count = state.uiState.recentSearches.size,
                key = { index -> state.uiState.recentSearches[index].searchTitle  + state.uiState.recentSearches[index].searchType.name }
            ) { index ->
                val recentSearch = state.uiState.recentSearches[index]
                RecentSearchItem(
                    modifier = Modifier.animateItem(),
                    recentSearchTitle = recentSearch.searchTitle + if (recentSearch.searchType != SearchType.Query) " (${recentSearch.searchType.name})" else "",
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
                if (index < state.uiState.recentSearches.size - 1) {
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