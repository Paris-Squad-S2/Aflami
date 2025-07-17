package com.datasource.remote.movie.service

import com.datasource.remote.movie.model.MovieCreditsDto
import com.datasource.remote.movie.model.MovieDto
import com.datasource.remote.movie.model.MovieImagesDto
import com.datasource.remote.movie.model.MovieReviewsDto
import com.datasource.remote.movie.model.MovieSimilarsDto
import com.datasource.remote.movie.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorMovieDetailsApiServiceImpl(
    private val httpClient: HttpClient,
) : KtorMovieDetailsApiService {
    override suspend fun getMovieDetails(movieId: Int, language: String): MovieDto {
        return safeApiCall {
            httpClient.get(MOVIE_PATH.replace(MOVIE_ID, movieId.toString())) {
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getMovieImages(movieId: Int): MovieImagesDto {
        return safeApiCall { httpClient.get(MOVIE_PATH.replace(MOVIE_ID, movieId.toString()) + IMAGES) }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int, language: String): MovieReviewsDto {
        return safeApiCall {
            httpClient.get(MOVIE_PATH.replace(MOVIE_ID, movieId.toString()) + REVIEWS) {
                parameter(PAGE, page)
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        language: String
    ): MovieSimilarsDto {
        return safeApiCall {
            httpClient.get(MOVIE_PATH.replace(MOVIE_ID, movieId.toString()) + SIMILAR) {
                parameter(PAGE, page)
                parameter(LANGUAGE, language)
            }
        }
    }

    override suspend fun getMovieCredits(movieId: Int, language: String): MovieCreditsDto {
        return safeApiCall {
            httpClient.get(MOVIE_PATH.replace(MOVIE_ID, movieId.toString()) + CREDITS) {
                parameter(LANGUAGE, language)
            }
        }
    }

    companion object {
        private const val MOVIE_ID = "{movieId}"
        private const val MOVIE_PATH = "movie/$MOVIE_ID"
        private const val IMAGES = "/images"
        private const val REVIEWS = "/reviews"
        private const val SIMILAR = "/similar"
        private const val CREDITS = "/credits"
        private const val LANGUAGE = "language"
        private const val PAGE = "page"
    }
}