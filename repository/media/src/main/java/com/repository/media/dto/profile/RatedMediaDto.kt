package com.repository.media.dto.profile

data class RatedMediaDto(
    val adult: Boolean = true,
    val backdrop_path: String?,
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val original_language: String,
    val original_title: String?,
    val original_name: String?,
    val overview: String,
    val popularity: Double = 0.0,
    val poster_path: String?,
    val release_date: String?,
    val first_air_date: String?,
    val title: String?,
    val name: String?,
    val video: Boolean = true,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
    val rating: Int = 0
)
