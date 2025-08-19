package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.repositories.GamePointsRepository
import com.paris_2.domain.user.usecase.GetAccountIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetUserPointUseCase(
    private val gamePointsRepository: GamePointsRepository,
    private val getAccountIdUseCase: GetAccountIdUseCase,
) {
    operator fun invoke(): Flow<Int> = flow {
        val userId = getAccountIdUseCase() ?: -1
        if (userId == -1) {
            emit(0)
        } else {
            emitAll(gamePointsRepository.getUserGamePoints(userId))
        }
    }
}

