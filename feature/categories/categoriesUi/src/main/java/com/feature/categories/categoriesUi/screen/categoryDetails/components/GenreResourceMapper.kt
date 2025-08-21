package com.feature.categories.categoriesUi.screen.categoryDetails.components

import com.feature.categories.categoriesUi.R
import com.paris.domain.media.entity.Category

object CategoryResourceMapper {
    private val categoryToResourceMap = mapOf(
        Category.Action to R.drawable.ic_category_action,
        Category.Adventure to R.drawable.ic_category_adventure,
        Category.Animation to R.drawable.ic_category_animation,
        Category.Comedy to R.drawable.ic_category_comedy,
        Category.Crime to R.drawable.ic_category_crime,
        Category.Documentary to R.drawable.ic_category_documentary,
        Category.Drama to R.drawable.ic_category_drama,
        Category.Family to R.drawable.ic_category_family,
        Category.Fantasy to R.drawable.ic_category_fantasy,
        Category.History to R.drawable.ic_category_history,
        Category.Horror to R.drawable.ic_category_horror,
        Category.Music to R.drawable.ic_category_music,
        Category.Mystery to R.drawable.ic_category_mystery,
        Category.Romance to R.drawable.ic_category_romance,
        Category.ScienceFiction to R.drawable.ic_category_science_fiction,
        Category.TvMovie to R.drawable.ic_category_tv_movie,
        Category.Thriller to R.drawable.ic_category_thriller,
        Category.War to R.drawable.ic_category_war,
        Category.Western to R.drawable.ic_category_western,
        Category.ActionAdventure to R.drawable.ic_category_action_and_adventure,
        Category.Kids to R.drawable.ic_category_kids,
        Category.News to R.drawable.ic_category_news,
        Category.Reality to R.drawable.ic_category_reality,
        Category.ScifiFantasy to R.drawable.ic_category_fantasy,
        Category.Soap to R.drawable.ic_category_soap,
        Category.Talk to R.drawable.ic_category_talk,
        Category.WarPolitics to R.drawable.ic_category_war
    )

    fun getResourceId(category: Category): Int {
        return categoryToResourceMap[category] ?: R.drawable.ic_category_all
    }
}