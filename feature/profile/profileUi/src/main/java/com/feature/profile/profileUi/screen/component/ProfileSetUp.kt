package com.feature.profile.profileUi.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.InterActionListener
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ProfileSetUp(modifier: Modifier = Modifier, interactionListener: InterActionListener) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SettingsItem(
            onClick = interactionListener::onChooseLanguageClicked,
            title = "Language",
            icon = R.drawable.ic_translate,
            content = {
                AppText(
                    text = "ENG",
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.text.body,
                    modifier = Modifier.padding(end = 8.dp)
                )

                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = Theme.colors.text.hint,
                )
            }
        )
        SettingsItem(
            onClick = {},
            title = "App Theme",
            icon = R.drawable.ic_moon,
            content = {
                AppText(
                    text = "ENG",
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.text.body,
                    modifier = Modifier.padding(end = 8.dp)
                )

                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = Theme.colors.text.hint,
                )
            }
        )
        SettingsItem(
            onClick = {},
            title = "Setting",
            icon = R.drawable.ic_customize,
            content = {
                AppText(
                    text = "ENG",
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.text.body,
                    modifier = Modifier.padding(end = 8.dp)
                )

                AppIcon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = Theme.colors.text.hint,
                )
            }
        )
    }
}