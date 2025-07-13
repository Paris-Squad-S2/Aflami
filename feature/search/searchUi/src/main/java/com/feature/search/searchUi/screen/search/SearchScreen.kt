package com.feature.search.searchUi.screen.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.search.searchUi.screen.search.components.FilterDialog
import com.feature.search.searchUi.screen.search.components.NoSearchQueryContent
import com.feature.search.searchUi.screen.search.components.WithSearchQueryContent
import com.paris_2.aflami.designsystem.components.TextField
import com.paris_2.aflami.designsystem.components.TopAppBar
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale
import com.feature.search.searchUi.R as RSearchUi
import com.paris_2.aflami.designsystem.R as RDesignSystem


@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    val currentLanguage = remember { mutableStateOf(Locale.getDefault().language) }

    LaunchedEffect(currentLanguage.value) {
        viewModel.loadCategories()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val lang = Locale.getDefault().language
                if (lang != currentLanguage.value) {
                    currentLanguage.value = lang
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    SearchScreenContent(
        state = screenState.value,
        searchScreenInteractionListener = viewModel,
    )
}

@Composable
private fun SearchScreenContent(
    searchScreenInteractionListener: SearchScreenInteractionListener,
    state: SearchScreenState
) {
    if (state.uiState.showFilterDialog) {
        FilterDialog(
            state = state,
            searchScreenInteractionListener = searchScreenInteractionListener,
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
    ) {
        TopAppBar(
            modifier = Modifier
                .statusBarsPadding(),
            title = stringResource(RSearchUi.string.searchTitle),
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = searchScreenInteractionListener::onNavigateBack,
                )
            ),
        )
        TextField(
            value = state.uiState.searchQuery,
            onValueChange = searchScreenInteractionListener::onSearchQueryChange,
            placeholder = stringResource(RSearchUi.string.search),
            trailingIcon = RDesignSystem.drawable.ic_filter_vertical,
            onClickTrailingIcon = searchScreenInteractionListener::onFilterButtonClick,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        if (state.uiState.searchQuery.isEmpty()) {
            NoSearchQueryContent(
                state = state,
                searchScreenInteractionListener = searchScreenInteractionListener
            )
        } else {
            WithSearchQueryContent(
                state = state,
                searchScreenInteractionListener = searchScreenInteractionListener,
            )
        }
    }
}
