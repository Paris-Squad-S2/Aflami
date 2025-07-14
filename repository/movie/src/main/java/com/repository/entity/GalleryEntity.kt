package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery_table")
data class GalleryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val images: List<ImageEntity>,
)