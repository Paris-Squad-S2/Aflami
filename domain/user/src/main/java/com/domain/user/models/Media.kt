package com.domain.user.models

data class Media(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val releaseYear: String,
    val rating: Double,
    val mediaType: MediaType,
){
    enum class MediaType {
        MOVIE,
        TV_SHOW
    }
}
