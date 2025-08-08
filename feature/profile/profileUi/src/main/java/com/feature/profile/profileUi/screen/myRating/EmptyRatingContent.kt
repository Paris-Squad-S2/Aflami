package com.feature.profile.profileUi.screen.myRating

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme
import com.feature.profile.profileUi.R

@Composable
fun EmptyRatingContent(
    modifier: Modifier = Modifier,
    isMovie: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_rating),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            AppText(
                text = if (isMovie)stringResource(id = R.string.no_rated_movies_yet) else stringResource(id = R.string.no_rated_tv_yet),
                style = Theme.textStyle.title.medium,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AppText(
                text = if (isMovie) stringResource(R.string.open_a_movie)
                else stringResource(R.string.open_a_tv_show),
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(showBackground = true,showSystemUi = true)
fun EmptyRatingContentPreview(){
    EmptyRatingContent(isMovie = true)
}