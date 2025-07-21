package com.repository.movie.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.repository.movie.util.getCurrentDate
import kotlinx.datetime.LocalDateTime


@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val posterPath: String,
    val genres: List<GenreEntity>,
    val releaseDate: String,
    val movieCacheDate: LocalDateTime = getCurrentDate(),
    val runtime: Int,
    val country: String,
    val productionCompanies: List<ProductionCompanyEntity>,
    val language: String
)