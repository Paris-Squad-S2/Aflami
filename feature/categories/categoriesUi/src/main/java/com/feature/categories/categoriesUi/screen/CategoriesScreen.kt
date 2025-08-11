package com.feature.categories.categoriesUi.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.aflami.designsystem.components.AppText

@Composable
fun CategoriesScreen(
    viewModel: CategoriesScreenViewModel = hiltViewModel(),
){
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