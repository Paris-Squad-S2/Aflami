package com.repository.media

import com.repository.media.services.MovieApiService
import com.repository.media.datasource.remote.MovieRemoteDataSource
import com.repository.media.models.remote.movie.MovieCreditsDto
import com.repository.media.models.remote.movie.MovieDto
import com.repository.media.models.remote.movie.MovieImagesDto
import com.repository.media.models.remote.movie.MovieReviewsDto
import com.repository.media.models.remote.movie.MovieSimilarsDto
import com.repository.media.models.remote.movie.MovieVideoDto
import com.repository.media.models.remote.movie.RatingDto
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int, language: String): MovieDto {
        return movieApiService.getMovieDetails(movieId, language)
    }

    override suspend fun getMovieImages(movieId: Int): MovieImagesDto {
        return movieApiService.getMovieImages(movieId)
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto {
        return movieApiService.getMovieReviews(movieId, page, language)
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto {
        return movieApiService.getSimilarMovies(movieId, page, language)
    }

    override suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto {
        return movieApiService.getMovieCredits(movieId, language)
    }

    override suspend fun getTrailerVideoForMovie(movieId: Int): MovieVideoDto {
        return movieApiService.getTrailerVideoForMovie(movieId)
    }

    override suspend fun addRatingToMovie(movieId: Int, rating: Float): Boolean {
        val dto = RatingDto(value = rating)
        val response = movieApiService.addRatingToMovie(
            movieId = movieId,
            rating = dto
        )
        return !(response.statusCode != STATUS_CODE_SUCCESS && response.statusCode != STATUS_CODE_UPDATED)
    }

    override suspend fun deleteMovieRating(movieId: Int): Boolean {
        return movieApiService.deleteMovieRating(movieId).success
    }

    companion object {
        private const val STATUS_CODE_SUCCESS = 1
        private const val STATUS_CODE_UPDATED = 12
    }
}