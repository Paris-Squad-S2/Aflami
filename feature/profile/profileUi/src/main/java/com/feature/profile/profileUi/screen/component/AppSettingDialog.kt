package com.feature.profile.profileUi.screen.component

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.changepassword.ChangePasswordActivity
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun AppSettingDialog(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    if (isVisible) {
        AppDialog(onDismiss = onDismiss, title = R.string.setting, modifier = modifier) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SettingsItem(
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                ChangePasswordActivity::class.java
                            )
                        )
                    },
                    title = stringResource(R.string.change_password),
                    icon = R.drawable.ic_door_lock,
                    content = {

                        AppIcon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = Theme.colors.text.hint,
                        )
                    })
                SettingsItem(
                    onClick = {},
                    title = stringResource(R.string.content_restriction),
                    icon = R.drawable.ic_security,
                    content = {
                        AppIcon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = Theme.colors.text.hint,
                        )
                    })

                SettingsItem(
                    onClick = {},
                    title = stringResource(R.string.tired_of_watching),
                    icon = R.drawable.ic_dead,
                    content = {
                        AppText(
                            text = stringResource(R.string.logout),
                            style = Theme.textStyle.label.medium,
                            color = Theme.colors.primary,
                            modifier = Modifier.padding(end = 12.dp)
                        )

                    })

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AppSettingDialogPrev() {
    AppSettingDialog(isVisible = true, onDismiss = {})
}