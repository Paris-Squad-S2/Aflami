package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.categories.categoriesUi.screen.categoryDetails.components.CategoriesList
import com.feature.categories.categoriesUi.screen.categoryDetails.components.CategoryDetailsEmptyScreen
import com.feature.categories.categoriesUi.screen.categoryDetails.components.CategoryDetailsMediaList
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun CategoryDetailsScreen(
    category: CategoryUiState,
    viewModel: CategoryDetailsScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.initialCategory(category)
    }

    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CategoriesScreenContent(
        state = state.value,
        interactionListener = viewModel
    )
}

@Composable
private fun CategoriesScreenContent(
    state: CategoryDetailsScreenUIState,
    interactionListener: CategoryDetailsScreenInteractionListener,
) {
    val activity = LocalActivity.current
    val mediaList = state.categoryDetailsUIState.media.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AppTopBar(
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = { activity?.finish() }
                )
            ),
            title = stringResource(state.categoryDetailsUIState.title),
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CategoriesList(
                categories = state.categoryDetailsUIState.categories,
                selectedCategory = state.categoryDetailsUIState.selectedCategory,
                onCategorySelected = interactionListener::onCategorySelected,
            )
            AnimatedVisibility(
                modifier = Modifier
                    .padding(end = 16.dp),
                visible = state.categoryDetailsUIState.mediaVisibility,
                enter = slideInHorizontally { -it },
                exit = slideOutHorizontally { -it }
            ) {
                when {
                    mediaList.loadState.refresh is LoadState.Loading -> {
                        PageLoadingPlaceHolder(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    mediaList.loadState.refresh is LoadState.Error -> {
                        when ((mediaList.loadState.refresh as LoadState.Error).error) {
                            is NoInternetConnectionException -> {
                                NetworkError(
                                    modifier = Modifier.fillMaxSize(),
                                    onRetry = interactionListener::onRetry
                                )
                            }

                            else -> { //TODO: Handle other errors
                                CategoryDetailsEmptyScreen()
                            }
                        }
                    }

                    mediaList.itemSnapshotList.isEmpty() -> {
                        CategoryDetailsEmptyScreen()
                    }

                    else -> {
                        CategoryDetailsMediaList(
                            mediaList = mediaList,
                            onMediaSelected = interactionListener::onMediaSelected,
                        )
                    }
                }
            }
        }
    }
}

