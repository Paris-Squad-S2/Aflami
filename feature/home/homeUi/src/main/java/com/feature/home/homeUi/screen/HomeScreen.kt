package com.feature.home.homeUi.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.aflami.designsystem.components.MoodPicker
import com.paris_2.aflami.designsystem.components.Slider

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),

    ){
        Box {
            // Todo (Top Bar)
            Slider(
                items = TODO(),
                onClick = TODO(),
                modifier = TODO()
            )
        }

        // Todo ( Continue Watching )

        // Todo (Top Rated )

        // Todo ( Movie birthday )

        MoodPicker(
            modifier = TODO(),
            backgroundColor = TODO(),
            image = TODO(),
            onEmojiClick = TODO(),
            title = TODO(),
            question = TODO()
        )

        // Todo ( upComing )

        // Todo ( Bottom Bar )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}