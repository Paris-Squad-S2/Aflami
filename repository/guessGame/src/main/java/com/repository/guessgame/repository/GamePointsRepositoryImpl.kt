package com.repository.guessgame.repository

import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.exception.FailedException
import com.paris_2.domain.game.exception.GameException
import com.paris_2.domain.game.repositories.GamePointsRepository
import com.repository.guessgame.datasource.local.GamePointsLocalDataSource
import com.repository.guessgame.mapper.toDomain
import com.repository.guessgame.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GamePointsRepositoryImpl(
    private val gamePointsLocalDataSource: GamePointsLocalDataSource,
    ) : GamePointsRepository {
    override suspend fun saveUserGamePoints(userPoints: UserPoints) {
        return safeCall(FailedException("Failed to save user game points")) {
            gamePointsLocalDataSource.saveUserGamePoints(userPoints.toEntity())
        }
    }

    override fun getUserGamePoints(userId: Int): Flow<Int> {
        return gamePointsLocalDataSource.getUserGamePoints(userId)
            .catch { 
                throw FailedException("Failed to get user game points") 
            }
            .map { entity ->
                safeCall(FailedException("Failed to get user game points")) {
                    entity?.toDomain()?.gamePoints ?: 0
                }
            }
    }

    private suspend fun <T> safeCall(exception: GameException, call: suspend () -> T): T {

        return try {
            call()
        } catch (e: GameException) {
            throw e
        } catch (_: Exception) {
            throw exception
        }
    }
}