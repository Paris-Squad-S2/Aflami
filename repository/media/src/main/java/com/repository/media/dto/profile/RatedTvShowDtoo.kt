package com.repository.media.dto.profile

data class RatedTvShowDtoo(
    val page: Int,
    val results: List<TvShowResult>,
    val total_pages: Int,
    val total_results: Int
)