package com.datasource.local.guessGame.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.repository.guessgame.entity.UserGamePointsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamePointsDao {
    @Upsert
    suspend fun upsertUserGamePoints(userGamePoints: UserGamePointsEntity)

    @Query("SELECT * FROM user_game_points WHERE userId = :userId LIMIT 1")
    fun getUserGamePoints(userId: Int): Flow<UserGamePointsEntity?>
}