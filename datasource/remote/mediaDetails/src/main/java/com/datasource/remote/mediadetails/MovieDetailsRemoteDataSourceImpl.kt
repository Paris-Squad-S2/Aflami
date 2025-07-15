package com.datasource.remote.mediadetails

import com.datasource.remote.mediadetails.models.movie.MovieCreditsDto
import com.datasource.remote.mediadetails.models.movie.MovieDto
import com.datasource.remote.mediadetails.models.movie.MovieImagesDto
import com.datasource.remote.mediadetails.models.movie.MovieReviewsDto
import com.datasource.remote.mediadetails.models.movie.MovieSimilarsDto
import com.datasource.remote.mediadetails.service.KtorMovieDetailsApiService

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

