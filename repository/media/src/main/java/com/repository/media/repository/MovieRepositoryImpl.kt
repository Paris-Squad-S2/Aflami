package com.repository.media.repository

import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.entity.Movie
import com.paris_2.domain.media.entity.MovieSimilar
import com.paris_2.domain.media.entity.MovieVideo
import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.repository.MovieRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.local.MovieLocalDataSource
import com.repository.media.datasource.remote.MovieRemoteDataSource
import com.repository.media.mapper.toEntity
import com.repository.media.mapper.toLocalDto
import com.repository.media.models.local.moive.MovieGalleryEntity
import com.repository.media.util.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class MovieRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val settingLocalDataSource: SettingLocalDataSource,
) : MovieRepository {

    override suspend fun getMovieDetails(movieId: Int): Movie {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMovieDetails")) {
            val localMovie = movieLocalDataSource.getMovieById(movieId, language)

            if (localMovie != null) {
                localMovie.toEntity()
            } else {
                val remoteMovie = movieRemoteDataSource.getMovieDetails(movieId, language)
                movieLocalDataSource.addMovie(remoteMovie.toLocalDto(language))
                movieLocalDataSource.getMovieById(movieId, language)?.toEntity()
                    ?: throw FailedException("getMovieDetails")
            }
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMovieCast")) {
            val localCast = movieLocalDataSource.getCastByMovieId(movieId, language)

            if (localCast.isNotEmpty()) {
                localCast.map { it.toEntity() }
            } else {
                val remoteCast = movieRemoteDataSource.getMovieCredits(movieId, language)
                    .cast?.map { it.toEntity() } ?: emptyList()

                movieLocalDataSource.addMovieCast(remoteCast.map {
                    it.toLocalDto(
                        movieId,
                        language
                    )
                })
                movieLocalDataSource.getCastByMovieId(movieId, language).map { it.toEntity() }
            }

        }
    }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieSimilar> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMovieRecommendations")) {

            val localMoviesSimilar =
                movieLocalDataSource.getSimilarMovies(movieId, page, language)

            if (localMoviesSimilar.isNotEmpty()) {
                localMoviesSimilar.map { it.toEntity() }
            } else {
                val remoteMoviesSimilarDto =
                    movieRemoteDataSource.getSimilarMovies(movieId, page, language)

                val moviesSimilarToCache =
                    remoteMoviesSimilarDto.movieSimilarDto?.map {
                        it.toLocalDto(movieId, page, language)
                    }

                moviesSimilarToCache?.let {
                    movieLocalDataSource.addSimilarMovies(it)
                }

                movieLocalDataSource.getSimilarMovies(movieId, page, language)
                    .map { it.toEntity() }

            }
        }
    }

    override suspend fun getMovieGallery(movieId: Int): List<Image> {
        return safeCall(FailedException("getMovieGallery")) {
            val localGallery = movieLocalDataSource.getGalleryByMovieId(movieId)

            if (localGallery != null) {
                localGallery.toEntity()
            } else {
                val remoteGallery = movieRemoteDataSource.getMovieImages(movieId).toEntity()
                val remoteGalleryImages = remoteGallery
                movieLocalDataSource.addMovieGallery(
                    MovieGalleryEntity(
                        movieId = movieId,
                        images = remoteGalleryImages.map { it.toLocalDto() },
                    )
                )
                movieLocalDataSource.getGalleryByMovieId(movieId)?.toEntity()
                    ?: throw FailedException("getMovieGallery")
            }
        }
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getCompanyProducts")) {
            val localMovie = movieLocalDataSource.getMovieById(movieId, language)
            val localProductionCompanies = localMovie?.productionCompanies
            if (!localProductionCompanies.isNullOrEmpty()) {
                localProductionCompanies.map { it.toEntity() }
            } else {
                val remoteMovieDetails =
                    movieRemoteDataSource.getMovieDetails(movieId, language)
                val remoteProductionCompanies = remoteMovieDetails.productionCompanies

                if (remoteProductionCompanies != null) {

                    movieLocalDataSource.addMovie(remoteMovieDetails.toLocalDto(language))
                }

                movieLocalDataSource.getMovieById(
                    movieId,
                    language
                )?.productionCompanies?.map { it.toEntity() } ?: emptyList()
            }

        }
    }

    override suspend fun getMovieReview(movieId: Int, page: Int): List<Review> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getMovieReview")) {

            val localReviews = movieLocalDataSource.getReviewsByMovieId(movieId, language)

            if (!localReviews.isNullOrEmpty()) {
                localReviews.map { it.toEntity() }
            } else {
                val remoteReviewsResponse =
                    movieRemoteDataSource.getMovieReviews(movieId, page, language)
                val remoteReviews =
                    remoteReviewsResponse.results?.map { it.toEntity() } ?: emptyList()

                if (remoteReviews.isNotEmpty()) {
                    movieLocalDataSource.addMovieReviews(remoteReviews.map {
                        it.toLocalDto(
                            movieId,
                            language
                        )
                    })
                }

                movieLocalDataSource.getReviewsByMovieId(movieId, language)
                    ?.map { it.toEntity() } ?: emptyList()
            }
        }
    }

    override suspend fun getTrailerVideoForMovie(movieId: Int): List<MovieVideo> {
        return safeCall(FailedException("getTrailerVideoForMovie")) {
            movieRemoteDataSource.getTrailerVideoForMovie(movieId)
                .movieVideoResultDto
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun addRatingToMovie(movieId: Int, rating: Float) {
        movieRemoteDataSource
        return safeCall(FailedException("addRatingToMovie")) {
            movieRemoteDataSource.addRatingToMovie(
                movieId = movieId,
                rating = rating
            )
        }
    }

    override suspend fun deleteMovieRating(movieId: Int) {
        return safeCall(FailedException("deleteMovieRating")) {
            movieRemoteDataSource.deleteMovieRating(
                movieId = movieId
            )
        }
    }

    private suspend fun <T> safeCall(exception: AflamiException, call: suspend () -> T): T {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return try {
            call()
        } catch (e: AflamiException) {
            throw e
        } catch (_: Exception) {
            throw exception
        }
    }
}