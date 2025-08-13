package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository

class SaveUserGamePointsUseCase(
    private val repository: GamePointsRepository
) {
    suspend operator fun invoke(points: UserPoints) {
        repository.saveUserGamePoints(points)
    }
}