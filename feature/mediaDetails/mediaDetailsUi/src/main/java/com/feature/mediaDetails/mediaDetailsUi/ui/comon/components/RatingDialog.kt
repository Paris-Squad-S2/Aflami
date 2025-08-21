package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.components.AppDialog
import com.paris.aflami.designsystem.components.RatingBar
import com.paris.aflami.designsystem.components.AppText
import com.paris.aflami.designsystem.theme.Theme


@Composable
fun RatingDialog(
    currentRating: Float,
    onRatingChange: (Float) -> Unit,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppDialog(
        onDismiss = onDismiss,
        title = com.feature.mediaDetails.mediaDetailsUi.R.string.rate,
        modifier = modifier
    ){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            AppText(
                text = stringResource(com.feature.mediaDetails.mediaDetailsUi.R.string.select_how_much_you_like_it),
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            )
            RatingBar(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 24.dp)
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .align(Alignment.CenterHorizontally),
                rating = currentRating,
                starSize = 32.dp,
                spaceBetween = 12.dp,
                maxRating = 5,
                onRatingChange = onRatingChange
            )
            CustomButton(
                text = R.string.submit,
                onClick = onSubmit,
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview(){
    RatingDialog(
        onDismiss = {},
        currentRating = 1.5f,
        onRatingChange = {},
        onSubmit = {},
        modifier = Modifier,
    )
}