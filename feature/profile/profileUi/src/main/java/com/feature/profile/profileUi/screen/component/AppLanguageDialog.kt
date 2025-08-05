package com.feature.profile.profileUi.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton

import  com.paris_2.aflami.designsystem.R as resDesignSystem

@Composable
fun AppLanguageDialog(isVisible: Boolean, modifier: Modifier = Modifier) {
    if (isVisible) {
        AppDialog(onDismiss = {}, title = R.string.language, modifier = modifier) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                SelectionCard(
                    optionTitle = stringResource(R.string.english),
                    isSelected = true,
                    icon = resDesignSystem.drawable.ic_english_language,
                    onClick = {}
                )

                SelectionCard(
                    optionTitle = stringResource(R.string.arabic),
                    isSelected = false,
                    icon = resDesignSystem.drawable.ic_arabic_language,
                    onClick = {},
                )
                Spacer(modifier = Modifier.height(24.dp))
                CustomButton(
                    onClick = {},
                    text = R.string.apply,
                    type = ButtonType.TextButton,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AppThemeDialogPrev() {
    AppLanguageDialog(isVisible = true)
}