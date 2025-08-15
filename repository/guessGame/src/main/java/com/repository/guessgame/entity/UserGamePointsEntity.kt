package com.repository.guessgame.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_game_points")
data class UserGamePointsEntity(
    @PrimaryKey
    val userId: Int = 0,
    val gamePoints: Int
)