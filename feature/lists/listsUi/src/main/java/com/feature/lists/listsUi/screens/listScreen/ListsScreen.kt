package com.feature.lists.listsUi.screens.listScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.feature.lists.listsUi.R
import com.feature.lists.listsUi.screens.listScreen.components.CreateListDialog
import com.feature.lists.listsUi.screens.listScreen.components.ListCard
import com.paris_2.aflami.designsystem.components.AppSnackBar
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.NetworkError
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.PlaceholderView
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.Theme
import kotlinx.coroutines.delay
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun ListsScreen(viewModel: ListsViewModel = hiltViewModel()) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle().value

    ListsScreenContent(
        state = uiState,
        viewModel
    )
}

@Composable
private fun ListsScreenContent(
    state: ListScreenUIState,
    action: ListsInteractionListener
) {
    LaunchedEffect(state.snackBarSuccess, state.showSnackBar) {
        if (state.showSnackBar && state.snackBarSuccess) {
            delay(3000)
            action.onHideSnackBar()
        }
    }
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        AppTopBar(
            title = stringResource(R.string.lists),
            trailingIcons = if (!state.isLoading&&state.isLoggedIn) {
                listOf(
                    iconItemWithDefaults(icon = ImageVector.vectorResource(R.drawable.ic_plus), onClick = { action.onAddClicked() })
                )
            } else emptyList()

        )

        val lazyPagingItems = state.lists.collectAsLazyPagingItems()

        when {
            !state.isLoggedIn ->{
                LoggedOutContent(
                    modifier = Modifier.fillMaxSize(),
                    listInteractionListener = action,
                )
            }
            state.errorMessage != null || lazyPagingItems.loadState.hasError -> {
                NetworkError(
                    modifier = Modifier.fillMaxSize(),
                    onRetry = { action.onRetryLists() }
                )
            }

            state.isLoading || lazyPagingItems.loadState.refresh == LoadState.Loading -> {
                PageLoadingPlaceHolder(
                    modifier = Modifier.fillMaxSize()
                )
            }

            lazyPagingItems.itemCount == 0 -> {
                PlaceholderView(
                    modifier = Modifier.fillMaxSize(),
                    image = painterResource(R.drawable.img_empty_brain),
                    title = stringResource(R.string.no_items_yet),
                    subTitle = stringResource(R.string.create_your_first_list),
                    spacer = 24.dp,
                    imageSize = 188.dp
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
                    ) { index ->
                        val listItem = lazyPagingItems[index]
                        listItem?.let { list ->
                            ListCard(
                                title = list.title,
                                count = list.count,
                                onCardClick = { action.onListClicked(list.id.toString()) }
                            )
                        }
                    }
                }
            }
        }
    }

    CreateListDialog(
        onDismiss = action::onCreateListDismiss,
        onAddClicked = action::onCreateListConfirm,
        onListNameValueChange = action::onCreateListNameChange,
        buttonState = state.createListButtonState,
        listName = state.createListName,
        showDialog = state.showCreateListDialog
    )

    AnimatedVisibility(
        visible = state.showSnackBar,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        AppSnackBar(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp),
            text = if (state.snackBarSuccess) RDesignSystem.string.added_new_list_successfully else RDesignSystem.string.some_error_happened,
            isSuccess = state.snackBarSuccess,
            onClick = {
                action.onHideSnackBar()
            }
        )
    }
}

@Composable
fun LoggedOutContent(
    modifier: Modifier,
    listInteractionListener: ListsInteractionListener,
    ) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
        )
        AppText(
            text = stringResource(R.string.please_login_to_access_your_account_details_and_other_features),
            style = Theme.textStyle.body.small,
            color = Theme.colors.text.body,
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 48.dp),
            textAlign = TextAlign.Center
        )
        CustomButton(
            onClick = listInteractionListener::onLogoutApplyClicked,
            text = com.paris_2.aflami.designsystem.R.string.login,
            type = ButtonType.Secondary,
            modifier = Modifier.padding(top = 24.dp, bottom = 100.dp)
        )

    }
}