package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository
import kotlinx.coroutines.flow.first

class UpdatePointsUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(userId: Int?, points: Int) {
        val safeUserId = userId ?: -1
        val currentPoints = gamePointsRepository.getUserGamePoints(safeUserId).first()
        val updatedPoints = currentPoints + points
        gamePointsRepository.saveUserGamePoints(UserPoints(safeUserId, updatedPoints))
    }
}