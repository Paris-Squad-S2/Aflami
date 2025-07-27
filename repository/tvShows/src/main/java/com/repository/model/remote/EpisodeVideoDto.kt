package com.repository.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeVideoDto(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("results")
    val episodeVideoResultDto: List<EpisodeVideoResultDto>? = null
)