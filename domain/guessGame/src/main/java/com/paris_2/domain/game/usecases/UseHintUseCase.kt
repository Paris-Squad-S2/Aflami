package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository
import kotlinx.coroutines.flow.first

class UseHintUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(session: GameSession, userId: Int): Boolean {
        val points = gamePointsRepository.getUserGamePoints(userId).first()
        return if (points >= 10) {
            session.getCurrentQuestion()?.usedHint = true
            gamePointsRepository.saveUserGamePoints(
                UserPoints(userId, points - 10)
            )
            true
        } else false
    }
}