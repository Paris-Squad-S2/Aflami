package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.repositories.GamePointsRepository
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import kotlinx.coroutines.flow.Flow

class GetUserPointUseCase(
    private val gamePointsRepository: GamePointsRepository,
) {
    operator fun invoke(userId: Int): Flow<Int> {
        return gamePointsRepository.getUserGamePoints(userId)
    }
}

