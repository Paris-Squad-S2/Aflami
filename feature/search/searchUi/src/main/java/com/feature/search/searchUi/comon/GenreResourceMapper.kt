package com.feature.search.searchUi.comon

import com.feature.search.searchUi.R

object GenreResourceMapper {
    private val genreToResourceMap = mapOf(
        28 to R.drawable.ic_category_action,
        12 to R.drawable.ic_category_adventure,
        16 to R.drawable.ic_category_animation,
        35 to R.drawable.ic_category_comedy,
        80 to R.drawable.ic_category_crime,
        99 to R.drawable.ic_category_documentary,
        18 to R.drawable.ic_category_drama,
        10751 to R.drawable.ic_category_family,
        14 to R.drawable.ic_category_fantasy,
        36 to R.drawable.ic_category_history,
        27 to R.drawable.ic_category_horror,
        10402 to R.drawable.ic_category_music,
        9648 to R.drawable.ic_category_mystery,
        10749 to R.drawable.ic_category_romance,
        878 to R.drawable.ic_category_science_fiction,
        10770 to R.drawable.ic_category_tv_movie,
        53 to R.drawable.ic_category_thriller,
        10752 to R.drawable.ic_category_war,
        37 to R.drawable.ic_category_western,

        10759 to R.drawable.ic_category_action_and_adventure,
        10762 to R.drawable.ic_category_kids,
        10763 to R.drawable.ic_category_news,
        10764 to R.drawable.ic_category_reality,
        10765 to R.drawable.ic_category_fantasy,
        10766 to R.drawable.ic_category_soap,
        10767 to R.drawable.ic_category_talk,
        10768 to R.drawable.ic_category_war
    )

    fun getResourceId(genreId: Int): Int {
        return genreToResourceMap[genreId] ?: R.drawable.ic_category_all
    }
}