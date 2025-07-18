package com.datasource.remote.movie

import com.datasource.remote.movie.service.KtorMovieDetailsApiService
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarsDto

class MovieDetailsRemoteDataSourceImpl(
    private val ktorMovieDetailsApiService: KtorMovieDetailsApiService
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int, language: String): MovieDto {
        return ktorMovieDetailsApiService.getMovieDetails(movieId, language)
    }

    override suspend fun getMovieImages(movieId: Int): MovieImagesDto {
        return ktorMovieDetailsApiService.getMovieImages(movieId)
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto {
        return ktorMovieDetailsApiService.getMovieReviews(movieId, page, language)
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int, language: String): MovieSimilarsDto {
        return ktorMovieDetailsApiService.getSimilarMovies(movieId, page, language)
    }

    override suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto {
        return ktorMovieDetailsApiService.getMovieCredits(movieId, language)
    }
}

