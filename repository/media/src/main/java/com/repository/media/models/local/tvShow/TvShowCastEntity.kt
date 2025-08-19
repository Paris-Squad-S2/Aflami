package com.repository.media.models.local.tvShow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cast_tv_shows_table", foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TvShowCastEntity(
    @PrimaryKey()
    val id: Int,
    val tvShowId: Int,
    val name: String,
    val imageUri: String,
    val language: String
)
