package com.paris_2.domain.game.usecases

class GetActorsMedia(
    private val getPopularActorsUseCase: GetPopularActorsUseCase
) {
    suspend operator fun invoke() = getPopularActorsUseCase().flatMap { it.media }

}