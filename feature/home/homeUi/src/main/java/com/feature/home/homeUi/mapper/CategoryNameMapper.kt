package com.feature.home.homeUi.mapper

import com.paris_2.domain.media.entity.Category


fun Category.toDisplayName(): String = this.displayName

fun String.toGenerEnum(): Category {
    return Category.entries.firstOrNull { it.displayName.equals(this, ignoreCase = true) }
        ?: Category.UNKNOWN
}



fun Int.genreToName(): String = when (this) {
    28 -> "Action"
    12 -> "Adventure"
    16 -> "Animation"
    35 -> "Comedy"
    80 -> "Crime"
    99 -> "Documentary"
    18 -> "Drama"
    10751 -> "Family"
    14 -> "Fantasy"
    36 -> "History"
    27 -> "Horror"
    10402 -> "Music"
    9648 -> "Mystery"
    10749 -> "Romance"
    878 -> "Science Fiction"
    10770 -> "TV Movie"
    53 -> "Thriller"
    10752 -> "War"
    37 -> "Western"
    10759 -> "Action & Adventure"
    10762 -> "Kids"
    10763 -> "News"
    10764 -> "Reality"
    10765 -> "Sci-Fi & Fantasy"
    10766 -> "Soap"
    10767 -> "Talk"
    10768 -> "War & Politics"

    else -> ""
}
fun String.nameToGenreId(): Int = when (this) {
    "Action" -> 28
    "Adventure" -> 12
    "Animation" -> 16
    "Comedy" -> 35
    "Crime" -> 80
    "Documentary" -> 99
    "Drama" -> 18
    "Family" -> 10751
    "Fantasy" -> 14
    "History" -> 36
    "Horror" -> 27
    "Music" -> 10402
    "Mystery" -> 9648
    "Romance" -> 10749
    "Science Fiction" -> 878
    "TV Movie" -> 10770
    "Thriller" -> 53
    "War" -> 10752
    "Western" -> 37
    "Action & Adventure" -> 10759
    "Kids" -> 10762
    "News" -> 10763
    "Reality" -> 10764
    "Sci-Fi & Fantasy" -> 10765
    "Soap" -> 10766
    "Talk" -> 10767
    "War & Politics" -> 10768
    else -> -1
}



