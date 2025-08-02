package com.domain.mediaDetails.useCases.movie

import com.domain.media.entity.MovieVideo
import com.domain.media.repository.MovieRepository

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