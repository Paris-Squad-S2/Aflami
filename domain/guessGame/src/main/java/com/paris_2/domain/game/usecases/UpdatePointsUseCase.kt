package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.repositories.GamePointsRepository
import kotlinx.coroutines.flow.first

class UpdatePointsUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(userId: Int, points: Int) {
        val userPoints = gamePointsRepository.getUserGamePoints(userId).first()
        val currentPoints = userPoints.gamePoints
        val updatedPoints = currentPoints + points
        gamePointsRepository.saveUserGamePoints(
            userPoints.copy(gamePoints = updatedPoints)
        )
    }
}