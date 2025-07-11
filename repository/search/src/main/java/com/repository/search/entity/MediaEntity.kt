package com.repository.search.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "media_table",
    foreignKeys = [
        ForeignKey(
            entity = SearchHistoryEntity::class,
            parentColumns = ["search_query"],
            childColumns = ["searchQuery"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val searchQuery: String,
    val imageUri: String,
    val title: String,
    val type: MediaTypeEntity,
    val category: List<Int>,
    val yearOfRelease: LocalDate,
    val rating: Double
)

enum class MediaTypeEntity {
    TVSHOW,
    MOVIE
}