package com.paris_2.domain.game.entity

import com.paris_2.domain.game.utils.Genre
import kotlinx.datetime.LocalDate

data class ActorMedia(
    val id: Int,
    val name: String,
    val posterImg: String,
    val yearOfRelease: LocalDate,
    val genres: List<Genre>
    )
