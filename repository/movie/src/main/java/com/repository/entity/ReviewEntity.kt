package com.repository.entity

import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ReviewEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val createdAt: LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
)