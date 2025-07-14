package com.repository.entity

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyEntity(
    @PrimaryKey
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String,
)