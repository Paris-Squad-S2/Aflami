package com.feature.search.searchUi.comon

import com.feature.search.searchUi.R
import com.paris_2.domain.media.entity.Category

object CategoryResourceMapper {
    private val categoryToResourceMap = mapOf(
        Category.ACTION to R.drawable.ic_category_action,
        Category.ADVENTURE to R.drawable.ic_category_adventure,
        Category.ANIMATION to R.drawable.ic_category_animation,
        Category.COMEDY to R.drawable.ic_category_comedy,
        Category.CRIME to R.drawable.ic_category_crime,
        Category.DOCUMENTARY to R.drawable.ic_category_documentary,
        Category.DRAMA to R.drawable.ic_category_drama,
        Category.FAMILY to R.drawable.ic_category_family,
        Category.FANTASY to R.drawable.ic_category_fantasy,
        Category.HISTORY to R.drawable.ic_category_history,
        Category.HORROR to R.drawable.ic_category_horror,
        Category.MUSIC to R.drawable.ic_category_music,
        Category.MYSTERY to R.drawable.ic_category_mystery,
        Category.ROMANCE to R.drawable.ic_category_romance,
        Category.SCIENCE_FICTION to R.drawable.ic_category_science_fiction,
        Category.TV_MOVIE to R.drawable.ic_category_tv_movie,
        Category.THRILLER to R.drawable.ic_category_thriller,
        Category.WAR to R.drawable.ic_category_war,
        Category.WESTERN to R.drawable.ic_category_western,
        Category.ACTION_ADVENTURE to R.drawable.ic_category_action_and_adventure,
        Category.KIDS to R.drawable.ic_category_kids,
        Category.NEWS to R.drawable.ic_category_news,
        Category.REALITY to R.drawable.ic_category_reality,
        Category.SCIFI_FANTASY to R.drawable.ic_category_fantasy,
        Category.SOAP to R.drawable.ic_category_soap,
        Category.TALK to R.drawable.ic_category_talk,
        Category.WAR_POLITICS to R.drawable.ic_category_war
    )

    fun getResourceId(category: Category): Int {
        return categoryToResourceMap[category] ?: R.drawable.ic_category_all
    }
}