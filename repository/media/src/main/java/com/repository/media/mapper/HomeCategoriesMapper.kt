package com.repository.media.mapper

import com.paris_2.domain.media.entity.Genre
import com.repository.media.dto.GenreDto
import com.repository.media.dto.GenresDto


fun GenreDto.toGenre(): Genre {
    return Genre.entries.find { it.displayName == this.name } ?: Genre.Unknown
}

fun List<GenreDto>.toGenreList(): List<Genre> {
    return this.map { it.toGenre() }
}

fun GenresDto.toGenreList(): List<Genre> {
    return this.genreDto?.toGenreList() ?: emptyList()
}
