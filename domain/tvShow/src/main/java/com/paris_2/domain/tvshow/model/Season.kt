package com.paris_2.domain.tvshow.model

data class Season(
    val id: Int,
    val name : String,
    val seasonNumber : Int,
    val episodeCount: Int,
    val tvShowEpisodes : List<Episode>,
)