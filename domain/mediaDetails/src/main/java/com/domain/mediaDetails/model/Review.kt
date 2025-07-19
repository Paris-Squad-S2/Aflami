package com.domain.mediaDetails.model

import kotlinx.datetime.LocalDate

data class Review(
    val id: String,
    val name: String,
    val createdAt :LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
    val description: String
)