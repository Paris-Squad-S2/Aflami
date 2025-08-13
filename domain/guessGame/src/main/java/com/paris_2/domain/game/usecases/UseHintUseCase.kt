package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository

class UseHintUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(session: GameSession, userId: Int) {
        val userPoints = gamePointsRepository.getUserGamePoints(userId)
        if (userPoints.gamePoints >= 10) {
            session.getCurrentQuestion()?.usedHint = true
            gamePointsRepository.saveUserGamePoints(
                userPoints = UserPoints(
                    userId = userId,
                    gamePoints = (userPoints.gamePoints - 10).coerceAtLeast(0)
                )
            )
        }
    }
}