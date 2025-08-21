package com.feature.profile.profileUi.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.Language
import com.paris.aflami.designsystem.components.AppDialog
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.theme.Theme

import  com.paris.aflami.designsystem.R as resDesignSystem

@Composable
fun AppLanguageDialog(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    languageState: Language,
    onLanguageSelected: (Language) -> Unit,
) {
    if (isVisible) {
        var dialogSelectedLanguage by remember(languageState) { mutableStateOf(languageState) }
        AppDialog(onDismiss = onDismiss, title = R.string.language, modifier = modifier) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                SelectionCard(
                    optionTitle = stringResource(R.string.english),
                    isSelected = dialogSelectedLanguage == Language.ENGLISH,
                    icon = resDesignSystem.drawable.ic_english_language,
                    onClick = {
                        dialogSelectedLanguage = Language.ENGLISH
                    }
                )

                SelectionCard(
                    optionTitle = stringResource(R.string.arabic),
                    isSelected = dialogSelectedLanguage == Language.ARABIC,
                    icon = resDesignSystem.drawable.ic_arabic_language,
                    onClick = {
                        dialogSelectedLanguage = Language.ARABIC
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                CustomButton(
                    onClick = {
                        onLanguageSelected(dialogSelectedLanguage)
                        onDismiss()
                    },
                    text = R.string.apply,
                    type = ButtonType.Primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Theme.colors.primary,
                                    Theme.colors.secondary
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AppThemeDialogPrev() {
    AppLanguageDialog(
        isVisible = true,
        onDismiss = {},
        languageState = Language.ENGLISH,
        onLanguageSelected = {}
    )

}