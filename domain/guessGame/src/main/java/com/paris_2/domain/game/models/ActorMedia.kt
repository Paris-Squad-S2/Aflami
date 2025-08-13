package com.paris_2.domain.game.models

import kotlinx.datetime.LocalDate

data class ActorMedia(
    val id: Int,
    val name: String,
    val posterImg: String,
    val yearOfRelease: LocalDate,
    )
