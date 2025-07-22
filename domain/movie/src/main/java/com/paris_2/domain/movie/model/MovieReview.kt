package com.paris_2.domain.movie.model

import kotlinx.datetime.LocalDate

data class MovieReview(
    val id: String,
    val name: String,
    val createdAt :LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
    val description: String
)