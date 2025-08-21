package com.paris.domain.game.repositories

import com.paris.domain.game.entity.UserPoints
import kotlinx.coroutines.flow.Flow

interface GamePointsRepository {
    suspend fun saveUserGamePoints(userPoints: UserPoints)
    fun getUserGamePoints(userId: Int): Flow<Int>
}