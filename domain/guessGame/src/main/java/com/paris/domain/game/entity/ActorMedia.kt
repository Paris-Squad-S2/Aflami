package com.paris.domain.game.entity

import com.paris.domain.media.entity.Category
import kotlinx.datetime.LocalDate

data class ActorMedia(
    val id: Int,
    val name: String,
    val posterImg: String,
    val yearOfRelease: LocalDate,
    val genres: List<Category>
    )
