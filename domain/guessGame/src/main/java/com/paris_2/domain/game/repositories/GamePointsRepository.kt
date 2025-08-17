package com.paris_2.domain.game.repositories

import com.paris_2.domain.game.entity.UserPoints
import kotlinx.coroutines.flow.Flow

interface GamePointsRepository {
    suspend fun saveUserGamePoints(userPoints: UserPoints)
    fun getUserGamePoints(userId: Int): Flow<UserPoints>
}