package com.repository.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.repository.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "cast_tv_shows_table", foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["id"],
        childColumns = ["tvShowId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CastEntity(
    @PrimaryKey
    val id: Int,
    val tvShowId: Int,
    val name: String,
    val imageUri: String,
    val castCacheDate: LocalDateTime = getCurrentDate(),
)
