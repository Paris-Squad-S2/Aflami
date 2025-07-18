package com.repository.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.repository.util.getCurrentDate
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "tv_shows_table")
data class TvShowEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val posterPath: String,
    val genres: List<GenreEntity>,
    val releaseDate: String,
    val tvShowCacheDate: LocalDateTime = getCurrentDate(),
    val runtime: Int,
    val country: String,
    val productionCompanies: List<ProductionCompanyEntity>,
)