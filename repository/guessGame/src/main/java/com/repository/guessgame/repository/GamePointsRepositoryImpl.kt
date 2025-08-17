package com.repository.guessgame.repository

import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository
import com.repository.guessgame.datasource.local.GamePointsLocalDataSource
import com.repository.guessgame.mapper.toDomain
import com.repository.guessgame.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GamePointsRepositoryImpl(
    private val gamePointsLocalDataSource: GamePointsLocalDataSource
) : GamePointsRepository {
    override suspend fun saveUserGamePoints(userPoints: UserPoints) {
        gamePointsLocalDataSource.saveUserGamePoints(userPoints.toEntity())
    }

    override fun getUserGamePoints(userId: Int): Flow<UserPoints> {
        return gamePointsLocalDataSource.getUserGamePoints(userId).map { entity ->
            entity?.toDomain() ?: UserPoints(userId = userId, gamePoints = 0)
        }
    }
}