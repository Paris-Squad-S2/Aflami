package com.feature.lists.listsUi.screens.listDetails

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.lists.listsUi.R
import com.feature.lists.listsUi.screens.listDetails.components.DeleteListDialog
import com.feature.lists.listsUi.screens.listDetails.components.ListDetailsResultContent
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun ListDetailsScreen(viewModel: ListDetailsViewModel = hiltViewModel()) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    ListDetailsScreenContent(
        state = screenState.value,
        listDetailsScreenInteractionListener = viewModel,
    )
}

@Composable
fun ListDetailsScreenContent(
    state: ListDetailsScreenState,
    listDetailsScreenInteractionListener: ListDetailsScreenInteractionListener,
) {
    val pagingItems = state.mediaItems.collectAsLazyPagingItems()
    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .navigationBarsPadding()
    ) {
        AppTopBar(
            modifier = Modifier
                .statusBarsPadding(),
            title = state.listTitle,
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(RDesignSystem.drawable.ic_back),
                    onClick = listDetailsScreenInteractionListener::onBackClick,
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(R.drawable.ic_delete),
                    onClick = listDetailsScreenInteractionListener::onDeleteListClick,
                )
            )
        )
        if (pagingItems.loadState.refresh == LoadState.Loading) {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        }
        else if (state.errorMessage != null || pagingItems.loadState.hasError) {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = listDetailsScreenInteractionListener::onRetryListsDetails
            )
        }
        else if (pagingItems.itemCount == 0) {
            PlaceholderView(
                modifier = Modifier
                    .fillMaxSize(),
                image = painterResource(R.drawable.img_empty_brain),
                title = stringResource(R.string.no_items_yet),
                spacer = 24.dp,
                imageSize = 188.dp
            )
        }
        else {
            ListDetailsResultContent(
                listDetailResult = pagingItems,
                onMediaCardClick = listDetailsScreenInteractionListener::onMediaCardClick,
                onRemoveClick = listDetailsScreenInteractionListener::onRemoveClick,
                nsfwThreshold = state.nsfwThreshold,
                genderThreshold = state.genderThreshold,
            )
        }
    }

    DeleteListDialog(
        onDismiss = listDetailsScreenInteractionListener::onDeleteDialogDismiss,
        onDeleteClicked = listDetailsScreenInteractionListener::onDeleteDialogConfirm,
        showDialog = state.showDeleteDialog
    )
}