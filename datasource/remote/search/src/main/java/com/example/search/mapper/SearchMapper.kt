package com.example.search.mapper

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.repository.search.models.ResultDto

fun com.repository.search.models.ResultDto.toMediaOrNull(): Media? {
    if (this.id == null || this.posterPath == null || this.title == null) {
        return null
    }

    val mediaType = when (this.mediaType) {
        "tv" -> MediaType.TVSHOW
        "movie" -> MediaType.MOVIE
        else -> MediaType.MOVIE
    }

    return Media(
        id = this.id,
        imageUri = this.posterPath,
        title = this.title,
        type = mediaType,
        categories = mapGenreIdsToCategoryNames(this.genreIds),
        yearOfRelease = TODO(),
        rating = TODO()
    )
}

fun mapGenreIdsToCategoryNames(genreIds: List<Int>?): List<String> {
    if (genreIds.isNullOrEmpty()) return emptyList()
    val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )
    return genreIds.mapNotNull { genreMap[it] }
}