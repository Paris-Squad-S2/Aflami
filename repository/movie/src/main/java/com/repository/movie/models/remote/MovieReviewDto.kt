package com.repository.movie.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewDto(
    @SerialName("author") val author: String? = null,
    @SerialName("author_details") val authorDetails: MovieAuthorDetailsDto? = null,
    @SerialName("content") val content: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("url") val url: String? = null
)

