package com.domain.mediaDetails.model

data class Season(
    val id: String,
    val name : String,
    val episodeCount: Int,
    val episodes : List<Episode>,
)