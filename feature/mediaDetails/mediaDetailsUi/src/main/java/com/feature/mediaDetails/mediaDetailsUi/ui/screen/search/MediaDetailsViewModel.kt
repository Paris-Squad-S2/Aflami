package com.feature.mediaDetails.mediaDetailsUi.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.BaseViewModel

data class MediaDetailsViewModelScreenState(
    val uiState: String,
    val isLoading: Boolean,
    val errorMessage: String?
)

class MediaDetailsViewModelViewModel(
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<MediaDetailsViewModelScreenState>(
        MediaDetailsViewModelScreenState(
            uiState = "",
            isLoading = false,
            errorMessage = null
        )
    ) {

    init {
        val mediaId =
            savedStateHandle.toRoute<MediaDetailsDestinations.MediaDetailsScreen>().mediaId
        emitState(
            MediaDetailsViewModelScreenState(
                uiState = "Media ID: $mediaId",
                isLoading = false,
                errorMessage = null
            )
        )
    }

    fun onNavigate() {
        navigate(
            MediaDetailsDestinations.MediaDetailsScreen(
                mediaId = 123
            )
        )
    }
}