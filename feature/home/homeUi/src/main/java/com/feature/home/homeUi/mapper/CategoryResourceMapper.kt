package com.feature.home.homeUi.mapper

import com.feature.home.homeUi.R
import com.paris_2.domain.media.entity.Genre

object CategoryResourceMapper {
    private val categoryToResourceMap = mapOf(
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

    fun getResourceId(genre: Genre): Int {
        return categoryToResourceMap[genre] ?: R.drawable.ic_category_all
    }
}