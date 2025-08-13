package com.paris_2.domain.game.usecases.guessMovieByPoster

import com.paris_2.domain.game.entity.MoviePosterQuestion
import com.paris_2.domain.game.repositories.ActorPopularityRepository

class GuessMovieSessionUseCase(
    private val actorPopularityRepository: ActorPopularityRepository
) {
    suspend operator fun invoke(count: Int): List<MoviePosterQuestion> {
        val actors = actorPopularityRepository.getPopularActor()
        val allMovies = actors.flatMap { it.media }

        return (0 until count).map {
            val correctMovie = allMovies.random()
            val wrongOptions = allMovies
                .filter { it.id != correctMovie.id }
                .shuffled()
                .take(3)
                .map { it.name }

            MoviePosterQuestion(
                posterImg = correctMovie.posterImg,
                options = (wrongOptions + correctMovie.name).shuffled(),
                correctAnswer = correctMovie.name
            )
        }
    }
}