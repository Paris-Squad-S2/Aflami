package com.paris.domain.game.usecases

class GetActorsMediaUseCase(
    private val getPopularActorsUseCase: GetPopularActorsUseCase
) {
    suspend operator fun invoke() = getPopularActorsUseCase().flatMap { it.media }

}