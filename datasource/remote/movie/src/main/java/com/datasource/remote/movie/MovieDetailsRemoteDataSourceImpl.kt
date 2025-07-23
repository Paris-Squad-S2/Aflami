package com.datasource.remote.movie

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto

class MovieDetailsRemoteDataSourceImpl(
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
}

