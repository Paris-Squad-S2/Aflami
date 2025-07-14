package com.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


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
    val runtime: Int,
    val country: CountryEntity,
    val productionCompanies: List<ProductionCompanyEntity>,
)