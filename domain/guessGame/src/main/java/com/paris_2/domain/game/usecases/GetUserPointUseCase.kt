package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.repositories.GamePointsRepository

class GetUserPointUseCase(
    private val gamePointsRepository: GamePointsRepository
) {
    suspend operator fun invoke(userId: Int): Int {
        return gamePointsRepository.getUserGamePoints(userId).gamePoints
    }
}