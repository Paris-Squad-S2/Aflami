package com.paris_2.domain.game.entity

data class Actor(
    val id : Int,
    val name : String,
    val imageUri : String,
    val media : List<ActorMedia>
)
