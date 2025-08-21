package com.repository.media.models.remote.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoResultDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("iso_3166_1")
    val countryCode: String? = null,
    @SerialName("iso_639_1")
    val languageCode: String? = null,
    @SerialName("key")
    val key: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("official")
    val official: Boolean? = null,
    @SerialName("published_at")
    val publishedAt: String? = null,
    @SerialName("site")
    val site: String? = null,
    @SerialName("size")
    val size: Int? = null,
    @SerialName("type")
    val type: String? = null
)