package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImageEntity(
    @PrimaryKey
    val id: Int,
    val url: String,
)