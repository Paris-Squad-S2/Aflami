package com.example.movie.models.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movie.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

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
    val galleryCacheDate: LocalDateTime = getCurrentDate(),
    )