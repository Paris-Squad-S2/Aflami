package com.feature.categories.categoriesUi.screen.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.domain.media.entity.Category

@Composable
fun CategoriesScreen(
    viewModel: CategoriesScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CategoriesScreenContent(
        state = state.value,
        action = viewModel
    )
}

@Composable
fun CategoriesScreenContent(
    state: CategoriesScreenUIState,
    action: CategoriesScreenInteractionListener,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppText(
            text = "Categories",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@PreviewLightDark
fun CategoriesScreenPreview() {
    CategoriesScreenContent(
        state = CategoriesScreenUIState(),
        action = object : CategoriesScreenInteractionListener {
            override fun onCategoryClick(category: Category) {
                TODO("Not yet implemented")
            }
        }
    )
}