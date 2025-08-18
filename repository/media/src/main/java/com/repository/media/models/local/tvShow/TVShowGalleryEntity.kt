package com.repository.media.models.local.tvShow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_gallery_table",
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TVShowGalleryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tvShowId: Int,
    val images: List<TVImageEntity>,
)