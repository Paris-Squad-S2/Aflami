package com.paris.domain.media.useCase.movie

import com.paris.domain.media.entity.MediaVideo
import com.paris.domain.media.repository.MovieRepository

class GetMovieVideoUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MediaVideo {
        return movieRepository.getTrailerVideoForMovie(movieId)
            .first { it.site.trim().lowercase() == SITE }

    }
    companion object{
        private const val SITE = "youtube"
    }
}