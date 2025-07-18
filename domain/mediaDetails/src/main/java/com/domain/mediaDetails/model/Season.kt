package com.domain.mediaDetails.model

data class Season(
    val id: Int,
    val name : String,
    val seasonNumber : Int,
    val episodeCount: Int,
    val episodes : List<Episode>,
)