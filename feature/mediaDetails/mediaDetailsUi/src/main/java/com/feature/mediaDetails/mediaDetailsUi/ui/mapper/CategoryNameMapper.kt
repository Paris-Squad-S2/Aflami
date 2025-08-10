package com.feature.mediaDetails.mediaDetailsUi.ui.mapper

import com.paris_2.domain.media.entity.Category


fun Category.toUiCategory(): CategoryMediaUi = when (this) {
    Category.Action -> CategoryMediaUi.Action
    Category.Adventure -> CategoryMediaUi.Adventure
    Category.Animation -> CategoryMediaUi.Animation
    Category.Comedy -> CategoryMediaUi.Comedy
    Category.Crime -> CategoryMediaUi.Crime
    Category.Documentary -> CategoryMediaUi.Documentary
    Category.Drama -> CategoryMediaUi.Drama
    Category.Family -> CategoryMediaUi.Family
    Category.Fantasy -> CategoryMediaUi.Fantasy
    Category.History -> CategoryMediaUi.History
    Category.Horror -> CategoryMediaUi.Horror
    Category.Music -> CategoryMediaUi.Music
    Category.Mystery -> CategoryMediaUi.Mystery
    Category.Romance -> CategoryMediaUi.Romance
    Category.ScienceFiction -> CategoryMediaUi.ScienceFiction
    Category.TvMovie -> CategoryMediaUi.TvMovie
    Category.Thriller -> CategoryMediaUi.Thriller
    Category.War -> CategoryMediaUi.War
    Category.Western -> CategoryMediaUi.Western
    Category.ActionAdventure -> CategoryMediaUi.ActionAdventure
    Category.Kids -> CategoryMediaUi.Kids
    Category.News -> CategoryMediaUi.News
    Category.Reality -> CategoryMediaUi.Reality
    Category.ScifiFantasy -> CategoryMediaUi.ScifiFantasy
    Category.Soap -> CategoryMediaUi.Soap
    Category.Talk -> CategoryMediaUi.Talk
    Category.WarPolitics -> CategoryMediaUi.WarPolitics
    Category.Unknown -> CategoryMediaUi.Unknown
}

fun Category.toDisplayName(): Int = this.toUiCategory().displayNameResId
