package com.feature.categories.categoriesUi.screen.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.CategoryCard
import com.paris_2.aflami.designsystem.components.TabRow

@Composable
fun CategoriesScreen(
    viewModel: CategoriesScreenViewModel = hiltViewModel(),
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
            CategoriesList(
                categories = when (state.categoriesUIState.selectedTabIndex) {
                    0 -> state.categoriesUIState.moviesCategories
                    else -> state.categoriesUIState.tvShowsCategories
                },
                onCategoryClick = interactionListener::onCategoryClick
            )
        }
    }
}

@Composable
fun CategoriesList(categories: List<CategoryUiState>, onCategoryClick: (CategoryUiState) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
    ) {

        items(categories) { category ->
            CategoryCard(
                categoryName = stringResource(category.name),
                categoryImage = painterResource(category.icon),
                onCategoryClick = { onCategoryClick(category) },
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onCategoryClick(category) }
            )
        }
    }
}