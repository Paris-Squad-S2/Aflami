package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.AppTextField
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.R as RDesignSystem

@Composable
fun CreateListDialog(
    onDismiss: () -> Unit,
    onAddClicked: () -> Unit,
    onListNameValueChange: (String) -> Unit,
    buttonState: ButtonState,
    listName: String,
    showDialog: Boolean
) {
    if (!showDialog) return
    AppDialog(
        onDismiss = onDismiss,
        title = R.string.create_new_list,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ){
            AppTextField(
                value = listName,
                onValueChange = onListNameValueChange,
                placeholder = stringResource(R.string.list_title),
                leadingIcon = RDesignSystem.drawable.ic_lists,
                modifier = Modifier
            )
            CustomButton(
                text = R.string.add,
                onClick =onAddClicked,
                type = ButtonType.Primary,
                state =buttonState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp).padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginDialog() {
    CreateListDialog(
        onDismiss = {},
        onAddClicked = {},
        onListNameValueChange = {},
        listName = "Sample List",
        showDialog = true,
        buttonState = ButtonState.Normal
    )
}
