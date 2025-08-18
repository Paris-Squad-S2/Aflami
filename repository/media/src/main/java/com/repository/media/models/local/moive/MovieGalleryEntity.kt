package com.repository.media.models.local.moive

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.media.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "movie_gallery_table",
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MovieGalleryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val images: List<MovieImageEntity>,
    val galleryCacheDate: LocalDateTime = getCurrentDate(),
    )