package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.feature.guessGame.guessGameUi.R
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

private object DifficultyDialogDefaults {
    val chipShape = RoundedCornerShape(8.dp)
    val infoShape = RoundedCornerShape(12.dp)
    const val ANIMATION_DURATION = 250
    val chipSpacing = 12.dp
}

@Composable
fun DifficultyDialog(
    title: Int,
    selectedDifficulty: Int,
    onDismiss: () -> Unit,
    onClickButton: () -> Unit,
    onSelectChip: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current

    val easyId = R.string.Easy
    val mediumId = R.string.Medium
    val hardId = R.string.Hard

    val difficultyDetails = when (selectedDifficulty) {
        easyId -> R.string.five_questions_45_sec_5_points_per_question
        mediumId -> R.string.ten_questions_30_sec_10_points_per_question
        hardId -> R.string.twenty_questions_10_sec_20_points_per_question
        else -> 0
    }

    val difficulties = if (layoutDirection == LayoutDirection.Rtl)
        listOf(hardId, mediumId, easyId)
    else
        listOf(easyId, mediumId, hardId)

    AppDialog(
        onDismiss = onDismiss,
        title = title,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (layoutDirection == LayoutDirection.Rtl)
                    Arrangement.End else Arrangement.Start
            ) {
                difficulties.forEachIndexed { index, difficultyId ->
                    Chip(
                        title = stringResource(difficultyId),
                        isSelected = selectedDifficulty == difficultyId,
                        onClick = { onSelectChip(difficultyId) },
                        modifier = Modifier.padding(
                            start = if (layoutDirection == LayoutDirection.Rtl && index != 0) DifficultyDialogDefaults.chipSpacing else 0.dp,
                            end = if (layoutDirection == LayoutDirection.Ltr && index != difficulties.lastIndex) DifficultyDialogDefaults.chipSpacing else 0.dp
                        )
                    )
                }
            }

            if (difficultyDetails != 0) {
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .clip(DifficultyDialogDefaults.infoShape)
                        .background(Theme.colors.surfaceHigh)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_idea),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                        )
                        AppText(
                            text = stringResource(difficultyDetails),
                            style = Theme.textStyle.label.small,
                            color = Theme.colors.status.yellowAccent
                        )
                    }
                }
            }

            CustomButton(
                text = com.paris_2.aflami.designsystem.R.string.let_s_play,
                onClick = onClickButton,
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun Chip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.primary else Theme.colors.surfaceHigh,
        animationSpec = tween(durationMillis = DifficultyDialogDefaults.ANIMATION_DURATION),
        label = "ChipBackground"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.onPrimaryColors.onPrimary else Theme.colors.primary,
        animationSpec = tween(durationMillis = DifficultyDialogDefaults.ANIMATION_DURATION),
        label = "ChipText"
    )

    Box(
        modifier = modifier
            .clip(DifficultyDialogDefaults.chipShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AppText(
            text = title,
            color = textColor,
            style = Theme.textStyle.label.small,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(locale = "en")
@Preview(locale = "ar")
@PreviewLightDark
@Composable
private fun Preview() {
    AflamiTheme {
        DifficultyDialog(
            title = R.string.Choose_Difficulty_Level,
            selectedDifficulty = R.string.Easy,
            onDismiss = {},
            onClickButton = {},
            onSelectChip = {}
        )
    }
}
