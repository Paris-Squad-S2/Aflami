package com.feature.categories.categoriesUi.screen.categoryDetails

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.categories.categoriesUi.R
import com.feature.categories.categoriesUi.screen.categories.CategoriesScreenInteractionListener
import com.feature.categories.categoriesUi.shared.CategoryUiState
import com.feature.categories.categoriesUi.shared.Status
import com.paris_2.aflami.designsystem.color.Colors
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun CategoryDetailsScreen(
    category: CategoryUiState,
    viewModel: CategoryDetailsScreenViewModel = hiltViewModel(),
) {
    viewModel.initialCategory(category)
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CategoriesScreenContent(
        state = state.value,
        interactionListener = viewModel
    )
}

@Composable
fun CategoriesScreenContent(
    state: CategoryDetailsScreenUIState,
    interactionListener: CategoryDetailsScreenInteractionListener,
) {
    val activity = LocalActivity.current
    Column(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxSize()
            .statusBarsPadding()
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
                .padding(horizontal = 16.dp)
        ) {
            CategoriesList(
                categories = state.categoryDetailsUIState.categories,
            )
            AnimatedVisibility(
                visible = state.categoryDetailsUIState.mediaVisibility,
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it }
            ) {
                when (state.status) {
                    Status.Loading -> {
                        PageLoadingPlaceHolder(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Status.NetworkError -> {}
                    Status.UnknownError -> {}
                    Status.Normal -> {}
                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            PlaceholderView(
                                image = painterResource(R.drawable.img_state_no_items_for_this_genre),
                                subTitle = stringResource(R.string.no_items_for_this_genre),
                                imageSize = 144.dp,
                                spacer = 24.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriesList(categories: List<CategoryUiState>) {

}