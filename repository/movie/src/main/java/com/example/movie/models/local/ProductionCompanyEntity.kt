package com.example.movie.models.local

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyEntity(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String,
)