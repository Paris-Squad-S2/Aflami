package com.feature.categories.categoriesUi.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.aflami.designsystem.components.AppText

@Composable
fun CategoriesScreen(
    viewModel: CategoriesScreenViewModel = hiltViewModel(),
){
    val state = viewModel.screenState.collectAsStateWithLifecycle()
    CategoriesScreen(
        state = state.value,
        action = viewModel
    )
}

@Composable
fun CategoriesScreen(
    state: CategoriesScreenUIState,
    action: CategoriesScreenInteractionListener,
) {
    AppText(
        text = "Categories",
        modifier = Modifier.fillMaxSize(),
    )
}