package com.paris_2.aflami.designsystem.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun PlaceholderView(
    image: Painter,
    title: String? = null,
    subTitle: String? = null,
    spacer: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(
            painter = image,
            contentDescription = "Empty View Image",
            modifier = Modifier.padding(bottom = spacer)
        )
        title?.let {
            Text(
                text = title,
                style = Theme.textStyle.title.medium,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
        subTitle?.let {
            Text(
                text = subTitle,
                style = Theme.textStyle.body.medium,
                color = Theme.colors.text.body,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(name = "Light_Disconnect Screen", showBackground = true)
@Preview(name = "Dark_Disconnect Screen", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)@Composable
fun PreviewDisconnectScreen() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_disconnect),
                title = "Oops! It looks like you’re offline.",
                subTitle = "Check your connection and try again, we’ll be waiting with your movies and popcorn",
                spacer = 16.dp
            )
        }
    }
}

@Preview(name = "Light_World Tour", showBackground = true)
@Preview(name = "Dark_World Tour", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewWorldTour() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_world_tour),
                title = "Country tour",
                subTitle = "Start exploring the world movie by enter your favorite country in search bar",
                spacer = 16.dp
            )
        }
    }
}
@Preview(name = "Light_No Search Result", showBackground = true)
@Preview(name = "Dark_No Search Result", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewNoSearchResult() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_no_search_result),
                title = "No search result",
                subTitle = "please try with another keyword.",
                spacer = 16.dp
            )
        }
    }
}
@Preview(name = "Light_Find By Actor", showBackground = true)
@Preview(name = "Dark_Find By Actor", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewFindByActor() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_find_by_actor),
                title = "Find by actor",
                subTitle = "Start exploring your favorite actor’s movies and enjoy it.",
                spacer = 16.dp
            )
        }
    }
}
@Preview(name = "Light_No Previous Search", showBackground = true)
@Preview(name = "Dark_No Previous Search", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewNoPreviousSearch() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_no_search_result),
                subTitle = "Start exploring! Search for your favorite movies, series and shows",
                spacer = 12.dp
            )
        }
    }
}
@Preview(name = "Light_No List Items", showBackground = true)
@Preview(name = "Dark_No List Items", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewNoListItems() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_no_list_item),
                title = " No saved items here!",
                spacer = 24.dp
            )
        }
    }
}
@Preview(name = "Light_No Saved Lists", showBackground = true)
@Preview(name = "Dark_No Saved Lists", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewNoSavedLists() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_no_list_item),
                title = "No lists yet!",
                subTitle = "Our brain is still empty, Click on + and start saving your favorite items and shows you love!",
                spacer = 24.dp
            )
        }
    }
}
@Preview(name = "Light_Guest Profile", showBackground = true)
@Preview(name = "Dark_Guest Profile", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewGuestProfile() {
    AflamiTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PlaceholderView(
                image = painterResource(R.drawable.img_guest),
                subTitle = "Please login to access your account details and other features!",
                spacer = 12.dp
            )
        }
    }
}