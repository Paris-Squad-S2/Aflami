package com.feature.home.homeUi.mapper

// new code
enum class Genre(
    val id: Int,
    val displayName: String
) {
    Action(28, "Action"),
    Adventure(12, "Adventure"),
    Animation(16, "Animation"),
    Comedy(35, "Comedy"),
    Crime(80, "Crime"),
    Documentary(99, "Documentary"),
    Drama(18, "Drama"),
    Family(10751, "Family"),
    Fantasy(14, "Fantasy"),
    History(36, "History"),
    Horror(27, "Horror"),
    Music(10402, "Music"),
    Mystery(9648, "Mystery"),
    Romance(10749, "Romance"),
    ScienceFiction(878, "Science Fiction"),
    TVMovie(10770, "TV Movie"),
    Thriller(53, "Thriller"),
    War(10752, "War"),
    Western(37, "Western"),
    ActionAdventure(10759, "Action & Adventure"),
    Kids(10762, "Kids"),
    News(10763, "News"),
    Reality(10764, "Reality"),
    SciFiFantasy(10765, "Sci-Fi & Fantasy"),
    Soap(10766, "Soap"),
    Talk(10767, "Talk"),
    WarPolitics(10768, "War & Politics");
}

object GenreResourceMapper {
    fun Int.toGenre(): Genre? = Genre.entries.find { it.id == this }
    fun String.toGenre(): Genre? = Genre.entries.find { it.displayName == this }
}




