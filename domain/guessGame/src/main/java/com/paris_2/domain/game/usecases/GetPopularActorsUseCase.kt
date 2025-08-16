package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.repositories.ActorPopularityRepository

class GetPopularActorsUseCase(
    private val repository : ActorPopularityRepository
) {
    suspend operator fun invoke() = repository.getPopularActor()
}