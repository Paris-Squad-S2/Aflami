package com.feature.home.homeUi.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feature.home.homeUi.R
import com.feature.home.homeUi.screen.home.MediaUiState
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.Dialog
import com.paris_2.aflami.designsystem.components.MediaCard
import com.paris_2.aflami.designsystem.components.Text
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.MediaCardType
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun MoodPickerDialog(
    movie : MediaUiState,
    onDismiss: () -> Unit,
    onViewDetailsClick: () -> Unit,
    onGetAnotherMovieClick: () -> Unit,
){

    Dialog(
        onDismiss = onDismiss,
        title = R.string.mood_picker,
        modifier = Modifier
            .padding(12.dp),
    ) {
        Text(
            text = stringResource(R.string.movie_that_matches_your_mood_is),
            style = Theme.textStyle.body.medium,
            color = Theme.colors.text.body,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
        MediaCard(
            imageUri = movie.imageUri,
            rating = movie.rating.toFloat(),
            movieName = movie.title,
            mediaType = movie.type.toString(),
            year = movie.yearOfRelease.toString(),
            mediaCardType = MediaCardType.UP_COMING,
            showGradientFilter = false,
            modifier = Modifier
                .padding(12.dp),

        )
        Column(modifier = Modifier
            .padding(horizontal = 12.dp,vertical = 12.dp)) {
            CustomButton(
                text = R.string.view_details,
                onClick = onViewDetailsClick,
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            CustomButton(
                text = R.string.get_another_movie,
                onClick = onGetAnotherMovieClick,
                type = ButtonType.Secondary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodPickerDialogPreview() {
//    MoodPickerDialog()
}