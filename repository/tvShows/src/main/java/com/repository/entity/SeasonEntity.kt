package com.repository.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "seasons_table",
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SeasonEntity(
    @PrimaryKey
    val id: String,
    val tvShowId: Int,
    val name: String,
    val episodes: List<EpisodeEntity>,
)
