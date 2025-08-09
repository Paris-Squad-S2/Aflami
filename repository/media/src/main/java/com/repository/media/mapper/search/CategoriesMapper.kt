package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Genre
import com.repository.media.dto.GenreDto
import com.repository.media.dto.GenresDto

val genreIdMap = mapOf(
    28 to Genre.Action,
    12 to Genre.Adventure,
    16 to Genre.Animation,
    35 to Genre.Comedy,
    80 to Genre.Crime,
    99 to Genre.Documentary,
    18 to Genre.Drama,
    10751 to Genre.Family,
    14 to Genre.Fantasy,
    36 to Genre.History,
    27 to Genre.Horror,
    10402 to Genre.Music,
    9648 to Genre.Mystery,
    10749 to Genre.Romance,
    878 to Genre.ScienceFiction,
    10770 to Genre.TVMovie,
    53 to Genre.Thriller,
    10752 to Genre.War,
    37 to Genre.Western,
    10759 to Genre.ActionAdventure,
    10762 to Genre.Kids,
    10763 to Genre.News,
    10764 to Genre.Reality,
    10765 to Genre.SciFiFantasy,
    10766 to Genre.Soap,
    10767 to Genre.Talk,
    10768 to Genre.WarPolitics
)

private val genreToIdMap = genreIdMap.entries.associate { (id, genre) ->
    genre to id
}
fun genreToId(genre: Genre): Int = genreToIdMap[genre] ?: 0

fun genreFromId(id: Int): Genre {
    return genreIdMap[id] ?: Genre.Unknown
}

fun List<GenreDto>.toGenres(): List<Genre> {
    return this.mapNotNull { it.id?.let(::genreFromId) }
}

fun GenresDto.toGenresList(): List<Genre> {
    return this.genreDto?.toGenres() ?: emptyList()
}
