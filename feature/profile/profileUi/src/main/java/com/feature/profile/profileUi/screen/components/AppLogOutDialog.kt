package com.feature.profile.profileUi.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun AppLogOutDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    if (isVisible) {
        AppDialog(onDismiss = onDismiss, title = R.string.logout, modifier = modifier) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_warning),
                    contentDescription = null,
                )
                AppText(
                    text = stringResource(R.string.are_you_sure_to_continue_if_you_re_tired_of_us_just_know_we_re_not_we_will_be_here),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.text.body,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                )
                CustomButton(
                    onClick = {
                        onLogout()

                    },
                    modifier = Modifier.padding(top = 12.dp),
                    type = ButtonType.Secondary,
                    text = R.string.logout
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppLogOutDialogPrev() {
    BasePreview {
        AppLogOutDialog(
            isVisible = true,
            onDismiss = {},
            onLogout = {}
        )
    }
}