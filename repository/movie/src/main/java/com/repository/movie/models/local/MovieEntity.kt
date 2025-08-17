package com.repository.movie.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val description: String,
    val posterPath: String,
    val genres: List<MovieGenreEntity>,
    val releaseDate: String,
    val runtime: Int,
    val country: String,
    val productionCompanies: List<MovieProductionCompanyEntity>,
    val language: String
)