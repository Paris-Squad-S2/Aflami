package com.feature.mediaDetails.mediaDetailsUi.ui.screen

interface MediaUi {
    val posterUrl: String
    val rating: Float
    val title: String
    val releaseDate: String
}

data class SimilarMediaUI (
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath : String,
    val releaseDate : String,
)