package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.categories.categoriesUi.navigation.navigateToMediaDetails
import com.feature.categories.categoriesUi.screen.categories.CategoriesScreenInteractionListener
import com.feature.categories.categoriesUi.screen.categories.CategoriesScreenUIState
import com.feature.categories.categoriesUi.screen.categories.components.CategoriesList
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.TabRow

@Composable
fun CategoryDetailsScreen(
    category: CategoryUiState,
    viewModel: CategoryDetailsScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CategoriesScreenContent(
        state = state.value,
        interactionListener = viewModel
    )
}

@Composable
fun CategoriesScreenContent(
    state: CategoriesScreenUIState,
    interactionListener: CategoriesScreenInteractionListener,
) {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        AppTopBar(
            title = stringResource(R.string.categories),
        )

        val tabs = listOf(
            stringResource(R.string.movies),
            stringResource(R.string.tv_shows)
        )

        Column {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                onTabSelected = interactionListener::onSelectTab,
                selectedIndex = state.categoriesUIState.selectedTabIndex,
                tabItems = tabs
            )
            Box {
                this@Column.AnimatedVisibility(
                    visible = state.categoriesUIState.selectedTabIndex == 0,
                    enter = slideInHorizontally(initialOffsetX = { -it }),
                    exit = slideOutHorizontally(targetOffsetX = { -it }),
                    label = "MoviesTabVisibility"
                ) {
                    CategoriesList(
                        categories = state.categoriesUIState.moviesCategories,
                        onCategoryClick = ::navigateToMediaDetails
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = state.categoriesUIState.selectedTabIndex == 1,
                    enter = slideInHorizontally(initialOffsetX = { it }),
                    exit = slideOutHorizontally(targetOffsetX = { it }),
                    label = "TVShowsTabVisibility"
                ) {
                    CategoriesList(
                        categories = state.categoriesUIState.tvShowsCategories,
                        onCategoryClick = ::navigateToMediaDetails
                    )
                }
            }
        }
    }
}