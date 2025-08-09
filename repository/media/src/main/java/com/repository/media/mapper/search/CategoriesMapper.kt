package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Category
import com.repository.media.dto.GenreDto


fun List<GenreDto>.toCategories(): List<Category> {
    return this.map { it.toCategoryModel() }
}

fun GenreDto.toCategoryModel(): Category {
    return id?.toGenre() ?: Category.UNKNOWN
}

private val idToCategoryMap = mapOf(
    28 to Category.ACTION,
    12 to Category.ADVENTURE,
    16 to Category.ANIMATION,
    35 to Category.COMEDY,
    80 to Category.CRIME,
    99 to Category.DOCUMENTARY,
    18 to Category.DRAMA,
    10751 to Category.FAMILY,
    14 to Category.FANTASY,
    36 to Category.HISTORY,
    27 to Category.HORROR,
    10402 to Category.MUSIC,
    9648 to Category.MYSTERY,
    10749 to Category.ROMANCE,
    878 to Category.SCIENCE_FICTION,
    10770 to Category.TV_MOVIE,
    53 to Category.THRILLER,
    10752 to Category.WAR,
    37 to Category.WESTERN,
    10759 to Category.ACTION_ADVENTURE,
    10762 to Category.KIDS,
    10763 to Category.NEWS,
    10764 to Category.REALITY,
    10765 to Category.SCIFI_FANTASY,
    10766 to Category.SOAP,
    10767 to Category.TALK,
    10768 to Category.WAR_POLITICS
)

fun Int.toGenre(): Category = idToCategoryMap[this] ?: Category.UNKNOWN

fun Category.toId(): Int {
    return idToCategoryMap.entries.firstOrNull { it.value == this }?.key ?: -1
}