package com.feature.mediaDetails.mediaDetailsUi.ui

import androidx.compose.runtime.Composable
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestination
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavGraph

class MediaDetailsFeatureAPIImpl : MediaDetailsFeatureAPI {
    override operator fun invoke(mediaDetailsDestination: MediaDetailsDestination?): @Composable () -> Unit = {
        MediaDetailsNavGraph(mediaDetailsDestination = mediaDetailsDestination)
    }
}