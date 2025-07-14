package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Entity(tableName = "Reviews_Table")
data class ReviewEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val createdAt: LocalDate,
    val avatarUrl: String,
    val username: String,
    val rating: Double,
)