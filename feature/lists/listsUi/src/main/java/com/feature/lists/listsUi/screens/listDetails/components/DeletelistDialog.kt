package com.feature.lists.listsUi.screens.listDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.lists.listsUi.R
import com.paris.aflami.designsystem.components.AppDialog
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun DeleteListDialog(
    onDismiss: () -> Unit,
    onDeleteClicked: () -> Unit,
    showDialog: Boolean
) {
    if (!showDialog) return
    AppDialog(
        onDismiss = onDismiss,
        title = R.string.delete_list,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 24.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.img_warning),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 12.dp),
                )

            AppText(
                text = stringResource(R.string.confirm_delete),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                style = Theme.textStyle.title.small
            )
            CustomButton(
                text = R.string.delete,
                onClick = onDeleteClicked,
                type = ButtonType.Primary,
                isNegative = true,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginDialog() {
    DeleteListDialog(
        onDismiss = {},
        onDeleteClicked = {},
        showDialog = true,
    )
}