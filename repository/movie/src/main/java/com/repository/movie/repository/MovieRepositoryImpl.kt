package com.repository.movie.repository

import com.domain.mediaDetails.exception.NoGalleryMovieDetailsFoundException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.exception.NoMovieDetailsFoundException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.MovieSimilar
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.mapper.toLocalDto
import com.repository.movie.models.local.GalleryEntity
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
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieCastLocalDataSource: MovieCastLocalDataSource,
    private val movieGalleryLocalDataSource: MovieGalleryLocalDataSource,
    private val movieReviewLocalDataSource: MovieReviewLocalDataSource,
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        try {
            val localMovie = movieLocalDataSource.getMovie(movieId)
            if (localMovie != null && isDataFresh(localMovie.movieCacheDate)) {
                return localMovie.toEntity()
            }

            if (networkConnectionChecker.isConnected.value) {
                val movieRemote = movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
                movieLocalDataSource.addMovie(movieRemote.toLocalDto())
                return movieLocalDataSource.getMovie(movieId)?.toEntity()
                    ?: throw NoMovieDetailsFoundException()
            } else {
                throw NoInternetConnectionException()
            }
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoMovieDetailsFoundException()
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        try {
            val localCast = movieCastLocalDataSource.getCastByMovieId(movieId)
            if (localCast.isNotEmpty() && localCast.all { isDataFresh(it.castCacheDate) }) {
                return localCast.map { it.toEntity() }
            }

            if (networkConnectionChecker.isConnected.value) {
                val remoteCast = movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                    .cast?.map { it.toEntity() } ?: emptyList()
                movieCastLocalDataSource.addCast(remoteCast.map { it.toLocalDto() })
                return movieCastLocalDataSource.getCastByMovieId(movieId).map { it.toEntity() }
            } else {
                throw NoInternetConnectionException()
            }
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            return emptyList()
        }
    }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieSimilar> {
        return try {
            if (networkConnectionChecker.isConnected.value) {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    movieId, page, language
                ).movieSimilarDto?.map { it.toEntity() } ?: emptyList()
            } else {
                throw NoInternetConnectionException()
            }
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getMovieGallery(movieId: Int): Gallery {
        try {
            val localGallery = movieGalleryLocalDataSource.getGalleryByMovieId(movieId)
            if (localGallery != null && isDataFresh(localGallery.galleryCacheDate)) {
                return localGallery.toEntity()
            }

            if (networkConnectionChecker.isConnected.value) {
                val remoteImages =
                    movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity().images
                movieGalleryLocalDataSource.addGallery(
                    GalleryEntity(
                        movieId = movieId,
                        images = remoteImages.map { it.toLocalDto() },
                        id = 0
                    )
                )
                return movieGalleryLocalDataSource.getGalleryByMovieId(movieId)?.toEntity()
                    ?: throw NoGalleryMovieDetailsFoundException()
            } else {
                throw NoInternetConnectionException()
            }
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            throw NoGalleryMovieDetailsFoundException()
        }
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        return try {
            if (networkConnectionChecker.isConnected.value) {
                movieDetailsRemoteDataSource.getMovieDetails(
                    movieId, language
                ).productionCompanies?.map { it.toEntity() } ?: emptyList()
            } else {
                throw NoInternetConnectionException()
            }
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            emptyList()
        }
    }


    override suspend fun getMovieReview(movieId: Int, page: Int): List<Review> {
        try {
            val localReviews = movieReviewLocalDataSource.getReviewsForMovie(movieId)
            if (localReviews.isNotEmpty() && localReviews.all { isDataFresh(it.reviewCacheDate) }) {
                return localReviews.map { it.toEntity() }
            }

            if (networkConnectionChecker.isConnected.value) {
                val remoteReviews =
                    movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
                        .results?.map { it.toEntity() } ?: emptyList()
                movieReviewLocalDataSource.addReview(remoteReviews.map { it.toLocalDto() })
                return movieReviewLocalDataSource.getReviewsForMovie(movieId).map { it.toEntity() }
            } else {
                throw NoInternetConnectionException()
            }
        } catch (e: NoInternetConnectionException) {
            throw e
        } catch (_: Exception) {
            return emptyList()
        }
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }


    @OptIn(ExperimentalTime::class)
    private fun isDataFresh(date: LocalDateTime): Boolean {
        val timeZone = TimeZone.currentSystemDefault()
        return date.toInstant(timeZone)
            .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
    }
}