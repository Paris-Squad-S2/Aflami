package com.example.movie.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movie.util.getCurrentDate
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
    val country: CountryEntity,
    val productionCompanies: List<ProductionCompanyEntity>,
)