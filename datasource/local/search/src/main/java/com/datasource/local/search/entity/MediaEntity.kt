package com.datasource.local.search.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(tableName = "media_table")
data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageUri: String,
    val title: String,
    val type: MediaTypeEntity,
    val category: List<String>,
    val yearOfRelease: LocalDate,
    val rating: Double,
    val country: String,
    val actor: List<String>,
)
enum class MediaTypeEntity {
    TVSHOW,
    MOVIE
}