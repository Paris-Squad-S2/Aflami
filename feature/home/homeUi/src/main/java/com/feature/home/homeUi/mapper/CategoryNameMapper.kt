package com.feature.home.homeUi.mapper

import com.feature.home.homeUi.R
import com.paris.domain.media.entity.Category


fun Category.toUiCategory(): CategoryHomeUi = when (this) {
    Category.Action -> CategoryHomeUi.Action
    Category.Adventure -> CategoryHomeUi.Adventure
    Category.Animation -> CategoryHomeUi.Animation
    Category.Comedy -> CategoryHomeUi.Comedy
    Category.Crime -> CategoryHomeUi.Crime
    Category.Documentary -> CategoryHomeUi.Documentary
    Category.Drama -> CategoryHomeUi.Drama
    Category.Family -> CategoryHomeUi.Family
    Category.Fantasy -> CategoryHomeUi.Fantasy
    Category.History -> CategoryHomeUi.History
    Category.Horror -> CategoryHomeUi.Horror
    Category.Music -> CategoryHomeUi.Music
    Category.Mystery -> CategoryHomeUi.Mystery
    Category.Romance -> CategoryHomeUi.Romance
    Category.ScienceFiction -> CategoryHomeUi.ScienceFiction
    Category.TvMovie -> CategoryHomeUi.TvMovie
    Category.Thriller -> CategoryHomeUi.Thriller
    Category.War -> CategoryHomeUi.War
    Category.Western -> CategoryHomeUi.Western
    Category.ActionAdventure -> CategoryHomeUi.ActionAdventure
    Category.Kids -> CategoryHomeUi.Kids
    Category.News -> CategoryHomeUi.News
    Category.Reality -> CategoryHomeUi.Reality
    Category.ScifiFantasy -> CategoryHomeUi.ScifiFantasy
    Category.Soap -> CategoryHomeUi.Soap
    Category.Talk -> CategoryHomeUi.Talk
    Category.WarPolitics -> CategoryHomeUi.WarPolitics
    Category.Unknown -> CategoryHomeUi.Unknown
}

fun Category.toDisplayName(): Int = this.toUiCategory().displayNameResId


fun Int.toCategory(): Category = when (this) {
    R.string.category_action -> Category.Action
    R.string.category_adventure -> Category.Adventure
    R.string.category_animation -> Category.Animation
    R.string.category_comedy -> Category.Comedy
    R.string.category_crime -> Category.Crime
    R.string.category_documentary -> Category.Documentary
    R.string.category_drama -> Category.Drama
    R.string.category_family -> Category.Family
    R.string.category_fantasy -> Category.Fantasy
    R.string.category_history -> Category.History
    R.string.category_horror -> Category.Horror
    R.string.category_music -> Category.Music
    R.string.category_mystery -> Category.Mystery
    R.string.category_romance -> Category.Romance
    R.string.category_science_fiction -> Category.ScienceFiction
    R.string.category_tv_movie -> Category.TvMovie
    R.string.category_thriller -> Category.Thriller
    R.string.category_war -> Category.War
    R.string.category_western -> Category.Western
    R.string.category_action_adventure -> Category.ActionAdventure
    R.string.category_kids -> Category.Kids
    R.string.category_news -> Category.News
    R.string.category_reality -> Category.Reality
    R.string.category_scifi_fantasy -> Category.ScifiFantasy
    R.string.category_soap -> Category.Soap
    R.string.category_talk -> Category.Talk
    R.string.category_war_politics -> Category.WarPolitics
    else -> Category.Unknown
}
