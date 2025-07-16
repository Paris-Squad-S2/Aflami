package com.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "gallery_table",
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GalleryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val images: List<ImageEntity>,
)