package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.SelectionCard

@Composable
fun AddToListDialog(
    list: List<String>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    AppDialog(
        onDismiss = onDismiss,
        title = R.string.add_to_list,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            items(list.indices.toList()) { index ->
                SelectionCard(
                    optionTitle = list[index],
                    optionDescription = stringResource(com.paris_2.aflami.designsystem.R.string._11_item),
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    isSelected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                    }
                )
            }
        }
        CustomButton(
            text = R.string.add,
            onClick = {},
            type = ButtonType.Primary,
            state = ButtonState.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
        )
        CustomButton(
            text = R.string.create_new_list,
            onClick = {},
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
        list = listOf("My Favorite Movies", "Kittens"),
        onDismiss = {},
    )
}
