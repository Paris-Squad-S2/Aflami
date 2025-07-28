package com.datasource.remote.movie

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto
import javax.inject.Inject
import com.repository.movie.models.remote.RatingDto

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

    override suspend fun addRatingToMovie(movieId: Int, rating: Float, sessionId: String) {
        val dto = RatingDto(value = rating)
        val response = retrofitMovieDetailsApiService.addRatingToMovie(movieId, sessionId, dto)
        if (response.status_code != 1 && response.status_code != 12) {
            throw Exception("Server responded: ${response.status_message}")
        }
    }
}

