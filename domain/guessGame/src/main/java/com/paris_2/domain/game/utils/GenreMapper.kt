package com.paris_2.domain.game.utils

enum class Genre(val id: Int, val displayName: String) {
    ACTION(28, "Action"),
    ADVENTURE(12, "Adventure"),
    ANIMATION(16, "Animation"),
    COMEDY(35, "Comedy"),
    CRIME(80, "Crime"),
    DOCUMENTARY(99, "Documentary"),
    DRAMA(18, "Drama"),
    FAMILY(10751, "Family"),
    FANTASY(14, "Fantasy"),
    HISTORY(36, "History"),
    HORROR(27, "Horror"),
    MUSIC(10402, "Music"),
    MYSTERY(9648, "Mystery"),
    ROMANCE(10749, "Romance"),
    SCIENCE_FICTION(878, "Science Fiction"),
    TV_MOVIE(10770, "TV Movie"),
    THRILLER(53, "Thriller"),
    WAR(10752, "War"),
    WESTERN(37, "Western"),
    ACTION_ADVENTURE(10759, "Action & Adventure"),
    KIDS(10762, "Kids"),
    NEWS(10763, "News"),
    REALITY(10764, "Reality"),
    SCI_FI_FANTASY(10765, "Sci-Fi & Fantasy"),
    SOAP(10766, "Soap"),
    TALK(10767, "Talk"),
    WAR_POLITICS(10768, "War & Politics");

    companion object {
        fun fromId(id: Int): Genre? = values().find { it.id == id }
    }
}
