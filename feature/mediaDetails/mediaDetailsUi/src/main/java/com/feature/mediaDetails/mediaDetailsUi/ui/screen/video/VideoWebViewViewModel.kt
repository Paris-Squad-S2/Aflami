package com.feature.mediaDetails.mediaDetailsUi.ui.screen.video

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations

data class VideoWebUIState(
    val videoUrl: String = "",
)

class VideoWebViewViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<VideoWebUIState>(VideoWebUIState()) {


    private val key by lazy {
        savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>()
            .key
    }

    init {
        val site by lazy {
            savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>()
                .site
        }

        val key by lazy {
            savedStateHandle.toRoute<MediaDetailsDestinations.VideosScreen>()
                .key
        }
        updateState(
            screenState.value.copy(
                videoUrl = "https://www.$site.com/watch?v=$key"
            )
        )
    }

    fun onNavigateBack() {
        navigateUp()
    }
}
