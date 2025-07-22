package com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.video

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

data class VideoWebUIState(
    val videoUrl: String = "",
)

class VideoWebViewViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<VideoWebUIState>(VideoWebUIState()) {

    private val site by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieVideosScreen>()
            .site
    }

    private val key by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.MovieVideosScreen>()
            .key
    }

    init {
        updateState(
            screenState.value.copy(
                videoUrl = "https://www.$site.com//watch?v=$key"
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }
}