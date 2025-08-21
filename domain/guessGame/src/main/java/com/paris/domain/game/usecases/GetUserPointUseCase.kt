package com.paris.domain.game.usecases

import com.paris.domain.game.repositories.GamePointsRepository
import kotlinx.coroutines.flow.Flow

class GetUserPointUseCase(
    private val gamePointsRepository: GamePointsRepository,
) {
    operator fun invoke(userId: Int): Flow<Int> {
        return gamePointsRepository.getUserGamePoints(userId)
    }
}

