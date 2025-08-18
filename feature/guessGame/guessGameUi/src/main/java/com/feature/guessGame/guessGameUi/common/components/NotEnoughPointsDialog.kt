package com.feature.guessGame.guessGameUi.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.guessGame.guessGameUi.R
import com.paris_2.aflami.designsystem.components.AppDialog
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun NotEnoughPointsDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    title: Int = R.string.Not_enough_points,
) {
    AppDialog(
        onDismiss = onDismiss,
        title = title,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            AppText(
                text = stringResource(R.string.sorry_not_enough_points_hint),
                style = Theme.textStyle.body.medium,
                color = Theme.colors.text.body
            )
            CustomButton(
                text = R.string.OK,
                onClick = onConfirm,
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp)
            )
        }
    }
}


@PreviewLightDark
@Preview(locale = "ar")
@Composable
private fun PreviewNotEnoughPointsDialog() {
    AflamiTheme {
        NotEnoughPointsDialog(
            title = R.string.Not_enough_points,
            onDismiss = {},
            onConfirm = {}
        )
    }
}