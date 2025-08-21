package com.paris.domain.game.entity

data class Actor(
    val id : Int,
    val name : String,
    val imageUri : String,
    val media : List<ActorMedia>
)
