package com.feature.mediaDetails.mediaDetailsApi

import kotlinx.serialization.Serializable

sealed interface MediaDetailsDestinations : MediaDetailsGraph {

    @Serializable
    data object MediaDetailsGraph1 : MediaDetailsGraph

    @Serializable
    data class MediaDetailsScreen(val mediaId: Int) : MediaDetailsDestination
}