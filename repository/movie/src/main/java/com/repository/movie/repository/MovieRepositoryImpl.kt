package com.repository.movie.repository

import com.domain.mediaDetails.exception.NetworkException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.MovieSimilar
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.util.NetworkConnectionChecker
import com.repository.movie.util.detectLanguage
import com.repository.movie.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class MovieRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return safeCall {
            val movieRemote = movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
            movieRemote.toEntity()
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return safeCall {
            movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                .cast?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieSimilar> {
        return safeCall {
            movieDetailsRemoteDataSource.getSimilarMovies(
                movieId,
                page,
                language
            ).movieSimilarDto?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun getMovieGallery(movieId: Int): Gallery {
        return safeCall {
            movieDetailsRemoteDataSource.getMovieImages(movieId)
                .toEntity()
        }
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        return safeCall {
            movieDetailsRemoteDataSource.getMovieDetails(
                movieId,
                language
            ).productionCompanies
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun getMovieReview(movieId: Int, page: Int): List<Review> {
        return safeCall {
            movieDetailsRemoteDataSource.getMovieReviews(
                movieId,
                page,
                language
            ).results?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }

    private suspend fun <T> safeCall(call: suspend () -> T): T {
        return try {
            if (networkConnectionChecker.isConnected.value.not()) {
                throw NoInternetConnectionException()
            }
            call()
        } catch (_: NoInternetConnectionException) {
            throw NoInternetConnectionException()
        } catch (e: Exception) {
            throw NetworkException(e.message ?: "Unknown error")
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun isDataFresh(date: LocalDateTime): Boolean {
        val timeZone = TimeZone.currentSystemDefault()
        return date.toInstant(timeZone)
            .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
    }
}