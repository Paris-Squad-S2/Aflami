package com.repository.media.entity
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "home_media_table")
data class HomeMediaEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val posterPath: String,
    val releaseDate: String,
    val genreIds: List<Int>,
    val type: MediaTypeEntity,
    val category: Category,
)

enum class Category {
    POPULAR,
    TOP_RATED,
    UPCOMING
}