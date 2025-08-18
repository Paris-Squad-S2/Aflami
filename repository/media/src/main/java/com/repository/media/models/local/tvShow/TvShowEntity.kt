package com.repository.media.models.local.tvShow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows_table")
data class TvShowEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val description: String,
    val posterPath: String,
    val genres: List<TVGenreEntity>,
    val releaseDate: String,
    val runtime: Int,
    val seasons: List<SeasonEntity>,
    val country: String,
    val productionCompanies: List<TVProductionCompanyEntity>,
    val language: String
)