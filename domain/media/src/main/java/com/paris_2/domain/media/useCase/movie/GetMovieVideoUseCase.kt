package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.entity.MovieVideo
import com.paris_2.domain.media.repository.MovieRepository

class GetMovieVideoUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieVideo {
        return movieRepository.getTrailerVideoForMovie(movieId)
            .first { it.site.trim().lowercase() == SITE }

    }
    companion object{
        private const val SITE = "youtube"
    }
}