package com.repository.model.local

import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeEntity(
    @PrimaryKey
    val id: Int,
    val episodeNumber: Int,
    val posterUrl: String,
    val voteAverage: Double,
    val airDate: LocalDate,
    val runtime: Int,
    val description: String,
    val stillUrl: String,
)