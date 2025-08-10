package com.feature.mediaDetails.mediaDetailsUi.ui.mapper

import com.paris_2.domain.media.entity.Category


fun Category.toUiCategory(): CategoryMediaUi = when (this) {
    Category.ACTION -> CategoryMediaUi.ACTION
    Category.ADVENTURE -> CategoryMediaUi.ADVENTURE
    Category.ANIMATION -> CategoryMediaUi.ANIMATION
    Category.COMEDY -> CategoryMediaUi.COMEDY
    Category.CRIME -> CategoryMediaUi.CRIME
    Category.DOCUMENTARY -> CategoryMediaUi.DOCUMENTARY
    Category.DRAMA -> CategoryMediaUi.DRAMA
    Category.FAMILY -> CategoryMediaUi.FAMILY
    Category.FANTASY -> CategoryMediaUi.FANTASY
    Category.HISTORY -> CategoryMediaUi.HISTORY
    Category.HORROR -> CategoryMediaUi.HORROR
    Category.MUSIC -> CategoryMediaUi.MUSIC
    Category.MYSTERY -> CategoryMediaUi.MYSTERY
    Category.ROMANCE -> CategoryMediaUi.ROMANCE
    Category.SCIENCE_FICTION -> CategoryMediaUi.SCIENCE_FICTION
    Category.TV_MOVIE -> CategoryMediaUi.TV_MOVIE
    Category.THRILLER -> CategoryMediaUi.THRILLER
    Category.WAR -> CategoryMediaUi.WAR
    Category.WESTERN -> CategoryMediaUi.WESTERN
    Category.ACTION_ADVENTURE -> CategoryMediaUi.ACTION_ADVENTURE
    Category.KIDS -> CategoryMediaUi.KIDS
    Category.NEWS -> CategoryMediaUi.NEWS
    Category.REALITY -> CategoryMediaUi.REALITY
    Category.SCIFI_FANTASY -> CategoryMediaUi.SCIFI_FANTASY
    Category.SOAP -> CategoryMediaUi.SOAP
    Category.TALK -> CategoryMediaUi.TALK
    Category.WAR_POLITICS -> CategoryMediaUi.WAR_POLITICS
    Category.UNKNOWN -> CategoryMediaUi.UNKNOWN
}

fun Category.toDisplayName(): Int = this.toUiCategory().displayNameResId
