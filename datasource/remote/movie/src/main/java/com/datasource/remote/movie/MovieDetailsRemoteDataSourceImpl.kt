package com.datasource.remote.movie

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto
import com.repository.movie.models.remote.RatingDto
import javax.inject.Inject

class MovieDetailsRemoteDataSourceImpl @Inject constructor(
    private val retrofitMovieDetailsApiService: RetrofitMovieDetailsApiService
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int, language: String): MovieDto {
        return retrofitMovieDetailsApiService.getMovieDetails(movieId, language)
    }

    override suspend fun getMovieImages(movieId: Int): MovieImagesDto {
        return retrofitMovieDetailsApiService.getMovieImages(movieId)
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto {
        return retrofitMovieDetailsApiService.getMovieReviews(movieId, page, language)
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto {
        return retrofitMovieDetailsApiService.getSimilarMovies(movieId, page, language)
    }

    override suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto {
        return retrofitMovieDetailsApiService.getMovieCredits(movieId, language)
    }

    override suspend fun getTrailerVideoForMovie(movieId: Int): MovieVideoDto {
        return retrofitMovieDetailsApiService.getTrailerVideoForMovie(movieId)
    }

    override suspend fun addRatingToMovie(movieId: Int, rating: Float): Boolean {
        val dto = RatingDto(value = rating)
        val response = retrofitMovieDetailsApiService.addRatingToMovie(
            movieId = movieId,
            rating = dto
        )
        return !(response.statusCode != STATUS_CODE_SUCCESS && response.statusCode != STATUS_CODE_UPDATED)
    }

    companion object {
        private const val STATUS_CODE_SUCCESS = 1
        private const val STATUS_CODE_UPDATED = 12
    }
}

