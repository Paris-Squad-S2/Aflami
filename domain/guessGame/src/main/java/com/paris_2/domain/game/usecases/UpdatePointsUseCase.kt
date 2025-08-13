package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.repositories.GamePointsRepository

class UpdatePointsUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(userId: Int, points: Int) {
        val userPoints = gamePointsRepository.getUserGamePoints(userId)
        val updatedPoints = userPoints.gamePoints + points
        gamePointsRepository.saveUserGamePoints(
            userPoints.copy(
                gamePoints = updatedPoints
            )
        )
    }
}