package com.feature.home.homeUi.mapper

import com.feature.home.homeUi.R
import com.paris_2.domain.media.entity.Category


fun Category.toUiCategory(): CategoryHomeUi = when (this) {
    Category.ACTION -> CategoryHomeUi.ACTION
    Category.ADVENTURE -> CategoryHomeUi.ADVENTURE
    Category.ANIMATION -> CategoryHomeUi.ANIMATION
    Category.COMEDY -> CategoryHomeUi.COMEDY
    Category.CRIME -> CategoryHomeUi.CRIME
    Category.DOCUMENTARY -> CategoryHomeUi.DOCUMENTARY
    Category.DRAMA -> CategoryHomeUi.DRAMA
    Category.FAMILY -> CategoryHomeUi.FAMILY
    Category.FANTASY -> CategoryHomeUi.FANTASY
    Category.HISTORY -> CategoryHomeUi.HISTORY
    Category.HORROR -> CategoryHomeUi.HORROR
    Category.MUSIC -> CategoryHomeUi.MUSIC
    Category.MYSTERY -> CategoryHomeUi.MYSTERY
    Category.ROMANCE -> CategoryHomeUi.ROMANCE
    Category.SCIENCE_FICTION -> CategoryHomeUi.SCIENCE_FICTION
    Category.TV_MOVIE -> CategoryHomeUi.TV_MOVIE
    Category.THRILLER -> CategoryHomeUi.THRILLER
    Category.WAR -> CategoryHomeUi.WAR
    Category.WESTERN -> CategoryHomeUi.WESTERN
    Category.ACTION_ADVENTURE -> CategoryHomeUi.ACTION_ADVENTURE
    Category.KIDS -> CategoryHomeUi.KIDS
    Category.NEWS -> CategoryHomeUi.NEWS
    Category.REALITY -> CategoryHomeUi.REALITY
    Category.SCIFI_FANTASY -> CategoryHomeUi.SCIFI_FANTASY
    Category.SOAP -> CategoryHomeUi.SOAP
    Category.TALK -> CategoryHomeUi.TALK
    Category.WAR_POLITICS -> CategoryHomeUi.WAR_POLITICS
    Category.UNKNOWN -> CategoryHomeUi.UNKNOWN
}

fun Category.toDisplayName(): Int = this.toUiCategory().displayNameResId


fun Int.toCategory(): Category = when (this) {
    R.string.category_action -> Category.ACTION
    R.string.category_adventure -> Category.ADVENTURE
    R.string.category_animation -> Category.ANIMATION
    R.string.category_comedy -> Category.COMEDY
    R.string.category_crime -> Category.CRIME
    R.string.category_documentary -> Category.DOCUMENTARY
    R.string.category_drama -> Category.DRAMA
    R.string.category_family -> Category.FAMILY
    R.string.category_fantasy -> Category.FANTASY
    R.string.category_history -> Category.HISTORY
    R.string.category_horror -> Category.HORROR
    R.string.category_music -> Category.MUSIC
    R.string.category_mystery -> Category.MYSTERY
    R.string.category_romance -> Category.ROMANCE
    R.string.category_science_fiction -> Category.SCIENCE_FICTION
    R.string.category_tv_movie -> Category.TV_MOVIE
    R.string.category_thriller -> Category.THRILLER
    R.string.category_war -> Category.WAR
    R.string.category_western -> Category.WESTERN
    R.string.category_action_adventure -> Category.ACTION_ADVENTURE
    R.string.category_kids -> Category.KIDS
    R.string.category_news -> Category.NEWS
    R.string.category_reality -> Category.REALITY
    R.string.category_scifi_fantasy -> Category.SCIFI_FANTASY
    R.string.category_soap -> Category.SOAP
    R.string.category_talk -> Category.TALK
    R.string.category_war_politics -> Category.WAR_POLITICS
    else -> Category.UNKNOWN
}
