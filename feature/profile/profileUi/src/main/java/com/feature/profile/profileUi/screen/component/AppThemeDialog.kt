package com.feature.profile.profileUi.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris_2.aflami.designsystem.components.AppDialog

@Composable
fun AppThemeDialog(isVisible: Boolean, modifier: Modifier = Modifier) {
    if (isVisible) {
        AppDialog(onDismiss = {}, title = R.string.app_theme, modifier = modifier) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                SelectionCard(
                    optionTitle = stringResource(com.paris_2.aflami.designsystem.R.string.dark),
                    isSelected = true,
                    icon = com.paris_2.aflami.designsystem.R.drawable.ic_dark_theme,
                    onClick = {}
                )

                SelectionCard(
                    optionTitle = stringResource(com.paris_2.aflami.designsystem.R.string.light),
                    isSelected = false,
                    icon = com.paris_2.aflami.designsystem.R.drawable.ic_light_theme,
                    onClick = {},
                )


            }
        }
    }
}


@PreviewLightDark
@Composable
private fun AppThemeDialogPrev() {
    AppThemeDialog(isVisible = true)
}