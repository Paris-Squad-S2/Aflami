package com.feature.categories.categoriesUi.screen.categoryDetails

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
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CategoryDetailsScreenContent(
        state = state.value,
        action = viewModel
    )
}

@Composable
fun CategoryDetailsScreenContent(
    state: CategoryDetailsScreenUIState,
    action: CategoryDetailsScreenInteractionListener,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppText(
            text = "CategoryDetails",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@PreviewLightDark
fun CategoryDetailsScreenPreview() {
    CategoryDetailsScreenContent(
        state = CategoryDetailsScreenUIState(),
        action = object : CategoryDetailsScreenInteractionListener {
            override fun onCategoryClick(category: Category) {
                TODO("Not yet implemented")
            }
        }
    )
}