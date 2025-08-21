package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ListItemUi
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.components.AppDialog

@Composable
fun AddToListDialog(
    lists: List<ListItemUi>,
    selectedIndex: Int,
    onDismiss: () -> Unit,
    onListSelectionChanged: (Int) -> Unit,
    onAddToSelectedList: () -> Unit,
    onCreateNewList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppDialog(
        onDismiss = onDismiss,
        title = R.string.add_to_list,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalArrangement = spacedBy(8.dp)
        ) {
            items(lists.indices.toList()) { index ->
                SelectionCard(
                    optionTitle = lists[index].name,
                    optionDescription = stringResource(R.string.Item, lists[index].itemCount),
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    isSelected = selectedIndex == index,
                    onClick = {
                        val newIndex = if (selectedIndex == index) -1 else index
                        onListSelectionChanged(newIndex)
                    }
                )
            }
        }
        CustomButton(
            text = R.string.add,
            onClick = onAddToSelectedList,
            type = ButtonType.Primary,
            state = if (selectedIndex >= 0) ButtonState.Normal else ButtonState.Disabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
        )
        CustomButton(
            text = R.string.create_new_list,
            onClick = onCreateNewList,
            type = ButtonType.Secondary,
            state = ButtonState.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AddToListDialog(
        lists = listOf(
            ListItemUi("1", "My Favorite Movies", 10),
            ListItemUi("2", "Kittens", 5)
        ),
        selectedIndex = 0,
        onDismiss = {},
        onListSelectionChanged = {},
        onAddToSelectedList = {},
        onCreateNewList = {}
    )
}
