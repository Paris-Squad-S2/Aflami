package com.repository.model.local

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class TVProductionCompanyEntity(
    @PrimaryKey
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String,
)