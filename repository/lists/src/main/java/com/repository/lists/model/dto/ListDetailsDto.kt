package com.repository.lists.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListDetailsDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("items") val items: List<MediaDetailsDto>? = null,
    @SerialName("created_by") val createdBy: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("favorite_count") val favoriteCount: Int? = null,
    @SerialName("iso_639_1") val iso6391: String? = null,
    @SerialName("item_count") val itemCount: Int? = null,
    @SerialName("poster_path") val posterPath: String? = null
)