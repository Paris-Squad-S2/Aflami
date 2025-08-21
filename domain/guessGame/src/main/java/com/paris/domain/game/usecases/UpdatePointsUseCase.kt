package com.paris.domain.game.usecases

import com.paris.domain.game.entity.UserPoints
import com.paris.domain.game.repositories.GamePointsRepository
import kotlinx.coroutines.flow.first

class UpdatePointsUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(userId: Int, points: Int) {
        val currentPoints = gamePointsRepository.getUserGamePoints(userId).first()
        val updatedPoints = currentPoints + points
        gamePointsRepository.saveUserGamePoints(UserPoints(userId, updatedPoints))
    }
}