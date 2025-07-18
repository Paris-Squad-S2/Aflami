package com.repository.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

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
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tvShowId: Int,
    val name: String,
    val episodeCount: Int,
    val episodes: List<EpisodeEntity>,
    val seasonCacheDate: LocalDateTime = getCurrentDate(),
)
