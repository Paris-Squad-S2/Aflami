package com.feature.search.searchUi.screen.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.search.searchUi.R
import com.feature.search.searchUi.comon.GenreResourceMapper.getResourceId
import com.feature.search.searchUi.screen.search.SearchScreenInteractionListener
import com.feature.search.searchUi.screen.search.SearchScreenState
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.AflamiDialog
import com.paris_2.aflami.designsystem.components.AflamiText
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.Chips
import com.paris_2.aflami.designsystem.components.RatingBar
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun FilterDialog(
    searchScreenInteractionListener: SearchScreenInteractionListener,
    state: SearchScreenState,
) {
    var currentRating by remember { mutableFloatStateOf(state.searchUiState.selectedRating) }
    var currentCategories by remember { mutableStateOf(state.searchUiState.categories) }
    var isAllCategories by remember { mutableStateOf(state.searchUiState.isAllCategories) }
    AflamiDialog(
        onDismiss = searchScreenInteractionListener::onFilterButtonClick,
        title = R.string.filter_result,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            AflamiText(
                text = stringResource(R.string.imdb_rating),
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            )
            RatingBar(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .align(Alignment.CenterHorizontally),
                rating = currentRating,
                onRatingChange = { newRating ->
                    currentRating = newRating
                }
            )
            AflamiText(
                text = stringResource(R.string.genre),
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 24.dp),
            ) {
                item {
                    Chips(
                        title = stringResource(R.string.all),
                        icon = painterResource(R.drawable.ic_category_all),
                        isSelected = isAllCategories,
                        onClick = {
                            isAllCategories = !isAllCategories
                            if (isAllCategories) {
                                currentCategories = state.searchUiState.categories.mapValues { false }
                            }
                        }
                    )
                }
                items(currentCategories.size) { index ->
                    val category = currentCategories.keys.elementAt(index)
                    Chips(
                        title = category.name,
                        icon = painterResource(getResourceId(category.id)),
                        isSelected = currentCategories[category] ?: false,
                        onClick = {
                            isAllCategories = false
                            currentCategories = currentCategories.toMutableMap().apply {
                                this[category] = !(currentCategories[category] ?: false)
                            }
                        }
                    )
                }
            }
            AflamiButton(
                text = R.string.apply,
                onClick = {
                    searchScreenInteractionListener.onApplyFilterButtonClick(
                        selectedRating = currentRating,
                        isAllCategories = isAllCategories,
                        selectedCategories = currentCategories.filter { it.value }.keys.toList()
                    )
                },
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            )
            AflamiButton(
                text = R.string.clear,
                onClick = searchScreenInteractionListener::onClearFilterClick,
                type = ButtonType.Secondary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            )
        }
    }
}