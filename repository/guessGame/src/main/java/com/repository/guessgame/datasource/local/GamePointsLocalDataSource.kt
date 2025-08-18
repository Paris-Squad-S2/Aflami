package com.repository.guessgame.datasource.local

import com.repository.guessgame.entity.UserGamePointsEntity
import kotlinx.coroutines.flow.Flow

interface GamePointsLocalDataSource {
    suspend fun saveUserGamePoints(gamePoints: UserGamePointsEntity)
    fun getUserGamePoints(userId: Int): Flow<UserGamePointsEntity?>
}