package com.repository.media.mapper.search

import com.paris.domain.media.entity.Category
import com.repository.media.models.remote.media.GenreDto


fun List<GenreDto>.toCategories(): List<Category> {
    return this.map { it.toCategoryModel() }
}

fun GenreDto.toCategoryModel(): Category {
    return id?.toGenre() ?: Category.Unknown
}

private val idToCategoryMap = mapOf(
    28 to Category.Action,
    12 to Category.Adventure,
    16 to Category.Animation,
    35 to Category.Comedy,
    80 to Category.Crime,
    99 to Category.Documentary,
    18 to Category.Drama,
    10751 to Category.Family,
    14 to Category.Fantasy,
    36 to Category.History,
    27 to Category.Horror,
    10402 to Category.Music,
    9648 to Category.Mystery,
    10749 to Category.Romance,
    878 to Category.ScienceFiction,
    10770 to Category.TvMovie,
    53 to Category.Thriller,
    10752 to Category.War,
    37 to Category.Western,
    10759 to Category.ActionAdventure,
    10762 to Category.Kids,
    10763 to Category.News,
    10764 to Category.Reality,
    10765 to Category.ScifiFantasy,
    10766 to Category.Soap,
    10767 to Category.Talk,
    10768 to Category.WarPolitics
)

fun Int.toGenre(): Category = idToCategoryMap[this] ?: Category.Unknown

fun Category.toId(): Int {
    return idToCategoryMap.entries.firstOrNull { it.value == this }?.key ?: -1
}