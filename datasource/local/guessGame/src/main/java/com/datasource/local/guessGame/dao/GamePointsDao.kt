package com.datasource.local.guessGame.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.repository.guessgame.entity.UserGamePointsEntity

@Dao
interface GamePointsDao {
    @Upsert
    suspend fun upsertUserGamePoints(userGamePoints: UserGamePointsEntity)
}