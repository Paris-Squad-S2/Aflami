package com.feature.home.homeUi.screen.topRatingMovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AflamiTheme {
                TopRatingMoviesScreen()
            }
        }
    }
}