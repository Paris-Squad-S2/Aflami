package com.repository.guessgame.datasource.local

import com.repository.guessgame.entity.UserGamePointsEntity

interface GamePointsLocalDataSource {
    suspend fun saveUserGamePoints(gamePoints: UserGamePointsEntity)
    suspend fun getUserGamePoints(userId: Int): UserGamePointsEntity?
}