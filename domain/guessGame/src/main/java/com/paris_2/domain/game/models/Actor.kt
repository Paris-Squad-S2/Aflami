package com.paris_2.domain.game.models

data class Actor(
    val id : Int,
    val name : String,
    val imageUri : String,
    val media : List<ActorMedia>
)
