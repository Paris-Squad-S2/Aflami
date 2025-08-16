package com.paris_2.domain.game.repositories

import com.paris_2.domain.game.entity.UserPoints

interface GamePointsRepository {
    suspend fun saveUserGamePoints(userPoints: UserPoints)
    suspend fun getUserGamePoints(userId: Int): UserPoints
}