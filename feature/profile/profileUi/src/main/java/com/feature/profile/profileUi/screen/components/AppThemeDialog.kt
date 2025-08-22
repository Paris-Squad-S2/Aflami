package com.feature.profile.profileUi.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.profile.Appearance
import com.paris.aflami.designsystem.components.AppDialog
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.designsystem.theme.Theme

@Composable
fun AppThemeDialog(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onThemeSelected: (Appearance) -> Unit,
    themeState: Appearance,
) {
    if (isVisible) {
        var dialogSelectedTheme by remember(themeState) { mutableStateOf(themeState) }

        AppDialog(onDismiss = onDismiss, title = R.string.app_theme, modifier = modifier) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SelectionCard(
                    optionTitle = stringResource(com.paris.aflami.designsystem.R.string.dark),
                    isSelected = dialogSelectedTheme == Appearance.DARK,
                    icon = com.paris.aflami.designsystem.R.drawable.ic_dark_theme,
                    onClick = {
                        dialogSelectedTheme = Appearance.DARK
                    }
                )

                SelectionCard(
                    optionTitle = stringResource(com.paris.aflami.designsystem.R.string.light),
                    isSelected = dialogSelectedTheme == Appearance.LIGHT,
                    icon = com.paris.aflami.designsystem.R.drawable.ic_light_theme,
                    onClick = {
                        dialogSelectedTheme = Appearance.LIGHT
                    },
                )
                AnimatedVisibility(
                    visible = dialogSelectedTheme == Appearance.LIGHT,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Theme.colors.surfaceHigh)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_stars),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(24.dp)
                            )
                            AppText(
                                text = stringResource(R.string.you_will_be_attacked_by_a_group_of_bright_colors),
                                style = Theme.textStyle.label.small,
                                color = Theme.colors.status.yellowAccent
                            )
                        }
                    }
                }
                CustomButton(
                    onClick = {
                        onThemeSelected(dialogSelectedTheme)
                        onDismiss()
                    },
                    text = R.string.apply,
                    type = ButtonType.Primary,
                    modifier = Modifier
                        .padding(top = 24.dp)
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

@Preview
@Composable
private fun Preview() {
    AflamiTheme {
        AppThemeDialog(
            isVisible = true,
            onDismiss = {},
            onThemeSelected = {},
            themeState = Appearance.LIGHT
        )
    }
}

