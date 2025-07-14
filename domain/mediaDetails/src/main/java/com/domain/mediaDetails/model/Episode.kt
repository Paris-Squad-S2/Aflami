package com.domain.mediaDetails.model

import kotlinx.datetime.LocalDate

data class Episode(
    val id: Int,
    val episodeNumber : Int,
    val posterUrl: String,
    val voteAverage: Double,
    val airDate: LocalDate,
    val runtime : Int,
    val description : String,
    val stillUrl : String,
)