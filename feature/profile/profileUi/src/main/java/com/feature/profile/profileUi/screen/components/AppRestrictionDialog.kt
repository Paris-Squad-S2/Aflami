package com.feature.profile.profileUi.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.ContentRestriction
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun AppRestrictionDialog(
    isVisible: Boolean,
    restriction: ContentRestriction,
    modifier: Modifier = Modifier,
    onSave: (ContentRestriction) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var dialogRestriction by remember(restriction) { mutableStateOf(restriction) }

    if (isVisible){
        AppDialog(
            onDismiss = onDismiss,
            title = R.string.content_restriction,
            modifier = modifier
        ) {

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SelectionCard(
                    optionTitle = stringResource(R.string.strict),
                    optionDescription = stringResource(R.string.blurs_all_sensitive_content),
                    isSelected = dialogRestriction == ContentRestriction.Strict,
                    hasRadioButton = true,
                    onClick =  {dialogRestriction = ContentRestriction.Strict},
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                SelectionCard(
                    optionTitle = stringResource(R.string.moderate),
                    optionDescription = stringResource(R.string.blurs_explicit_scenes_only),
                    isSelected =  dialogRestriction == ContentRestriction.Moderate,
                    hasRadioButton = true,
                    onClick =  {dialogRestriction = ContentRestriction.Moderate},
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                SelectionCard(
                    optionTitle = stringResource(R.string.off),
                    optionDescription = stringResource(R.string.no_content_is_blurred),
                    isSelected =  dialogRestriction == ContentRestriction.Off,
                    hasRadioButton = true,
                    onClick = {dialogRestriction = ContentRestriction.Off},
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                CustomButton(
                    onClick = {
                        onSave(dialogRestriction)
                        onDismiss()
                    },
                    text = R.string.save,
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