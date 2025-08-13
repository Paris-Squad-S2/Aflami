package com.datasource.local.guessGame.dao

import androidx.room.Upsert
import com.repository.guessgame.entity.UserGamePointsEntity

interface GamePointsDao {
    @Upsert
    suspend fun upsertUserGamePoints(userGamePoints: UserGamePointsEntity)
}