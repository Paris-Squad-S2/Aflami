package com.paris.domain.game.usecases

import com.paris.domain.game.repositories.ActorPopularityRepository

class GetPopularActorsUseCase(
    private val repository : ActorPopularityRepository
) {
    suspend operator fun invoke() = repository.getPopularActor()
}