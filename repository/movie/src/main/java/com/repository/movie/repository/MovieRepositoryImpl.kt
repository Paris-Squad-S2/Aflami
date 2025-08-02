package com.repository.movie.repository

import com.domain.mediaDetails.exception.AflamiException
import com.domain.mediaDetails.exception.FailedToAddRatingException
import com.domain.mediaDetails.exception.NoCastFoundException
import com.domain.mediaDetails.exception.NoGalleryFoundException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.exception.NoMovieFoundException
import com.domain.mediaDetails.exception.NoProductionCompanyFoundException
import com.domain.mediaDetails.exception.NoReviewFoundException
import com.domain.mediaDetails.exception.NoVideoFoundException
import com.domain.mediaDetails.entity.Cast
import com.domain.mediaDetails.entity.Image
import com.domain.mediaDetails.entity.Movie
import com.domain.mediaDetails.entity.MovieSimilar
import com.domain.mediaDetails.entity.MovieVideo
import com.domain.mediaDetails.entity.ProductionCompany
import com.domain.mediaDetails.entity.Review
import com.domain.mediaDetails.repository.MovieRepository
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.mapper.toLocalDto
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.util.NetworkConnectionChecker
import com.repository.movie.util.detectLanguage

class MovieRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieCastLocalDataSource: MovieCastLocalDataSource,
    private val movieGalleryLocalDataSource: MovieGalleryLocalDataSource,
    private val movieReviewLocalDataSource: MovieReviewLocalDataSource,
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
    private val movieSimilarLocalDataSource: MovieSimilarLocalDataSource,
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return safeCall(NoMovieFoundException()) {
            val localMovie = movieLocalDataSource.getMovieById(movieId, language)

            if (localMovie != null) {
                localMovie.toEntity()
            } else {
                val remoteMovie = movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
                movieLocalDataSource.addMovie(remoteMovie.toLocalDto(language))
                movieLocalDataSource.getMovieById(movieId, language)?.toEntity()
                    ?: throw NoMovieFoundException()
            }
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return safeCall(NoCastFoundException()) {
            val localCast = movieCastLocalDataSource.getCastByMovieId(movieId, language)

            if (localCast.isNotEmpty()) {
                localCast.map { it.toEntity() }
            } else {
                val remoteCast = movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                    .cast?.map { it.toEntity() } ?: emptyList()

                movieCastLocalDataSource.addCast(remoteCast.map {
                    it.toLocalDto(
                        movieId,
                        language
                    )
                })
                movieCastLocalDataSource.getCastByMovieId(movieId, language).map { it.toEntity() }
            }

        }
    }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieSimilar> {
        return safeCall(NoMovieFoundException()) {

            val localMoviesSimilar =
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)

            if (localMoviesSimilar.isNotEmpty()) {
                localMoviesSimilar.map { it.toEntity() }
            } else {
                val remoteMoviesSimilarDto =
                    movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)

                val moviesSimilarToCache =
                    remoteMoviesSimilarDto.movieSimilarDto?.map {
                        it.toLocalDto(movieId, page, language)
                    }

                moviesSimilarToCache?.let {
                    movieSimilarLocalDataSource.addSimilarMovies(it)
                }

                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
                    .map { it.toEntity() }

            }
        }
    }

    override suspend fun getMovieGallery(movieId: Int): List<Image> {
        return safeCall(NoGalleryFoundException()) {
            val localGallery = movieGalleryLocalDataSource.getGalleryByMovieId(movieId)

            if (localGallery != null) {
                localGallery.toEntity()
            } else {
                val remoteGallery = movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity()
                val remoteGalleryImages = remoteGallery
                movieGalleryLocalDataSource.addGallery(
                    GalleryEntity(
                        movieId = movieId,
                        images = remoteGalleryImages.map { it.toLocalDto() },
                    )
                )
                movieGalleryLocalDataSource.getGalleryByMovieId(movieId)?.toEntity()
                    ?: throw NoGalleryFoundException()
            }
        }
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        return safeCall(NoProductionCompanyFoundException()) {
            val localMovie = movieLocalDataSource.getMovieById(movieId, language)
            val localProductionCompanies = localMovie?.productionCompanies
            if (!localProductionCompanies.isNullOrEmpty()) {
                localProductionCompanies.map { it.toEntity() }
            } else {
                val remoteMovieDetails =
                    movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
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
        return safeCall(NoReviewFoundException()) {

            val localReviews = movieReviewLocalDataSource.getReviewsForMovie(movieId, language)

            if (!localReviews.isNullOrEmpty()) {
                localReviews.map { it.toEntity() }
            } else {
                val remoteReviewsResponse =
                    movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
                val remoteReviews =
                    remoteReviewsResponse.results?.map { it.toEntity() } ?: emptyList()

                if (remoteReviews.isNotEmpty()) {
                    movieReviewLocalDataSource.addReview(remoteReviews.map {
                        it.toLocalDto(
                            movieId,
                            language
                        )
                    })
                }

                movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
                    ?.map { it.toEntity() } ?: emptyList()
            }
        }
    }

    override suspend fun getTrailerVideoForMovie(movieId: Int): List<MovieVideo> {
        return safeCall(NoVideoFoundException()) {
            movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId)
                .movieVideoResultDto
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun addRatingToMovie(movieId: Int, rating: Float) {
        movieDetailsRemoteDataSource
        return safeCall(FailedToAddRatingException()) {
            movieDetailsRemoteDataSource.addRatingToMovie(
                movieId = movieId,
                rating = rating
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