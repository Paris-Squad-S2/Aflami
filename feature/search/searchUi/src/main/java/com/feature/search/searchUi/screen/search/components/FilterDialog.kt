package com.feature.search.searchUi.screen.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.feature.search.searchUi.R
import com.feature.search.searchUi.screen.search.SearchScreenInteractionListener
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.AflamiDialog
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.RatingBar
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun FilterDialog(
    searchScreenInteractionListener: SearchScreenInteractionListener,
) {
    var currentRating by remember { mutableFloatStateOf(7.7f) }
    AflamiDialog(
        onDismiss = searchScreenInteractionListener::onFilterButtonClick,
        title = R.string.filter_result,
    ) {
        Column {
            Text(
                text = "IMDb rating",
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            )
            RatingBar( //TODO: Refactor it
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .align(Alignment.CenterHorizontally)
                ,
                rating = currentRating,
                onRatingChange = { newRating ->
                    currentRating = newRating
                }
            )
            Text(
                text = "Genre",
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            )
            //TODO: Implement Genre Selection
            AflamiButton(
                text = R.string.apply,
                onClick = { },
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            )
            AflamiButton(
                text = R.string.clear,
                onClick = searchScreenInteractionListener::onFilterButtonClick,
                type = ButtonType.Secondary,
                state = ButtonState.Normal,
                modifier = Modifier.fillMaxWidth()
                .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            )
        }
    }
}