package com.feature.search.searchUi.screen.worldTour

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
import java.util.Locale
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun WorldTourScreen(viewModel: WorldTourViewModel = koinViewModel()) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    WorldTourScreenContent(
        state = screenState.value,
        worldTourScreenInteractionListener = viewModel,
    )
}

@Composable
fun WorldTourScreenContent(
    state: WorldTourScreenState,
    worldTourScreenInteractionListener: WorldTourScreenInteractionListener
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
            title = stringResource(R.string.world_tour),
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = worldTourScreenInteractionListener::onNavigateBack,
                )
            ),
        )
        val isArabic = Locale.getDefault().language == "ar"
        TextField(
            value = state.uiState.searchQuery,
            onValueChange = worldTourScreenInteractionListener::onSearchQueryChange,
            placeholder = stringResource(R.string.search),
            suggestions = state.uiState.hints.map {
                val name = if (isArabic) {
                    it.arabicName
                } else {
                    it.englishName
                }
                name + " (${it.countryCode})" },
            onSuggestionSelected = {
                worldTourScreenInteractionListener.onSearchQueryChange(
                    it.substringBefore(
                        "("
                    ).trim()
                )
            },
        )
        if (state.uiState.searchQuery.isEmpty()) {
            PlaceholderView(
                modifier = Modifier
                    .fillMaxSize(),
                image = painterResource(RDesignSystem.drawable.img_world_tour),
                title = stringResource(R.string.country_tour),
                subTitle = stringResource(R.string.start_exploring_the_world_movie),
                spacer = 16.dp
            )
        } else if (state.errorMessage != null) {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = worldTourScreenInteractionListener::onRetrySearchQuery
            )
        } else if (state.isLoading) {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.uiState.searchResult.isEmpty()) {
            PlaceholderView(
                modifier = Modifier.fillMaxSize(),
                image = painterResource(RDesignSystem.drawable.img_no_search_result),
                title = stringResource(R.string.no_search_result),
                subTitle = stringResource(R.string.please_try_with_another_keyword),
                spacer = 16.dp
            )
        } else {
            SearchResultContent(
                searchResult = state.uiState.searchResult,
                onMediaCardClick = worldTourScreenInteractionListener::onMediaCardClick
            )
        }
    }
}