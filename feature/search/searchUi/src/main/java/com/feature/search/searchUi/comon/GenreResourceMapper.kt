package com.feature.search.searchUi.comon

import CategoryUiState
import com.feature.search.searchUi.R

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

    companion object {
        fun fromId(id: Int): Genre? = values().find { it.id == id }
        fun fromName(name: String): Genre? = values().find { it.displayName == name }
    }
}

object GenreResourceMapper {
    private val genreToResourceMap = mapOf(
        Genre.Action to R.drawable.ic_category_action,
        Genre.Adventure to R.drawable.ic_category_adventure,
        Genre.Animation to R.drawable.ic_category_animation,
        Genre.Comedy to R.drawable.ic_category_comedy,
        Genre.Crime to R.drawable.ic_category_crime,
        Genre.Documentary to R.drawable.ic_category_documentary,
        Genre.Drama to R.drawable.ic_category_drama,
        Genre.Family to R.drawable.ic_category_family,
        Genre.Fantasy to R.drawable.ic_category_fantasy,
        Genre.History to R.drawable.ic_category_history,
        Genre.Horror to R.drawable.ic_category_horror,
        Genre.Music to R.drawable.ic_category_music,
        Genre.Mystery to R.drawable.ic_category_mystery,
        Genre.Romance to R.drawable.ic_category_romance,
        Genre.ScienceFiction to R.drawable.ic_category_science_fiction,
        Genre.TVMovie to R.drawable.ic_category_tv_movie,
        Genre.Thriller to R.drawable.ic_category_thriller,
        Genre.War to R.drawable.ic_category_war,
        Genre.Western to R.drawable.ic_category_western,
        Genre.ActionAdventure to R.drawable.ic_category_action_and_adventure,
        Genre.Kids to R.drawable.ic_category_kids,
        Genre.News to R.drawable.ic_category_news,
        Genre.Reality to R.drawable.ic_category_reality,
        Genre.SciFiFantasy to R.drawable.ic_category_fantasy,
        Genre.Soap to R.drawable.ic_category_soap,
        Genre.Talk to R.drawable.ic_category_talk,
        Genre.WarPolitics to R.drawable.ic_category_war
    )

    fun getResourceId(genre: Genre?): Int {
        return genreToResourceMap[genre] ?: R.drawable.ic_category_all
    }
}

object CategoryGenreMapper {
    fun mapToGenre(category: CategoryUiState): Genre? {
        return Genre.values().find { it.id == category.id }
    }
}