package com.repository.movie.repository

import com.domain.mediaDetails.exception.NetworkException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.model.MovieSimilar
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
import kotlin.collections.isNotEmpty

class MovieRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieCastLocalDataSource: MovieCastLocalDataSource,
    private val movieGalleryLocalDataSource: MovieGalleryLocalDataSource,
    private val movieReviewLocalDataSource: MovieReviewLocalDataSource,
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
    private val movieSimilarLocalDataSource: MovieSimilarLocalDataSource
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return safeCall {
            val localMovie = movieLocalDataSource.getMovie(movieId, language)

            if (localMovie != null) {
                localMovie.toEntity()
            } else {
                val remoteMovie = movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
                movieLocalDataSource.addMovie(remoteMovie.toLocalDto(language))
                remoteMovie.toEntity()
            }
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return safeCall {
            val movie = movieLocalDataSource.getMovie(movieId, language)

            if (movie == null) {
                val remoteCast = movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                    .cast?.map { it.toEntity() } ?: emptyList()

                movieCastLocalDataSource.addCast(remoteCast.map { it.toLocalDto(language) })
                remoteCast
            } else {
                val localCast = movieCastLocalDataSource.getCastByMovieId(movieId, language)

                if (!localCast.isNullOrEmpty()) {
                    localCast.map { it.toEntity() }
                } else {
                    val remoteCast = movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                        .cast?.map { it.toEntity() } ?: emptyList()

                    movieCastLocalDataSource.addCast(remoteCast.map { it.toLocalDto(language) })
                    remoteCast
                }
            }
        }
    }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieSimilar> {
        return safeCall {

            val localMoviesSimilar =
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)

            if (localMoviesSimilar != null && localMoviesSimilar.isNotEmpty()) {
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

                remoteMoviesSimilarDto.movieSimilarDto?.map { it.toEntity() } ?: emptyList()

            }
        }
    }

    override suspend fun getMovieGallery(movieId: Int): Gallery {
        return safeCall {
            val localGallery = movieGalleryLocalDataSource.getGalleryByMovieId(movieId)

            if (localGallery != null) {
                localGallery.toEntity()
            } else {
                val remoteGallery = movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity()
                val remoteGalleryImages = remoteGallery.images
                remoteGallery.also {
                    movieGalleryLocalDataSource.addGallery(
                        GalleryEntity(
                            movieId = movieId,
                            images = remoteGalleryImages.map { it.toLocalDto() },
                        )
                    )
                }
            }
        }
    }

    override suspend fun getCompanyProducts(movieId: Int): List<ProductionCompany> {
        return safeCall {
            val localMovie = movieLocalDataSource.getMovie(movieId, language)
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

                remoteProductionCompanies?.map { it.toEntity() } ?: emptyList()
            }

        }
    }

    override suspend fun getMovieReview(movieId: Int, page: Int): List<Review> {
        return safeCall {
            val movie = movieLocalDataSource.getMovie(movieId, language)

            if (movie == null) {
                val remoteReviewsResponse =
                    movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
                val remoteReviews =
                    remoteReviewsResponse.results?.map { it.toEntity() } ?: emptyList()

                if (remoteReviews.isNotEmpty()) {
                    movieReviewLocalDataSource.addReview(remoteReviews.map { it.toLocalDto() })
                }

                remoteReviews
            } else {
                val localReviews = movieReviewLocalDataSource.getReviewsForMovie(movieId)

                if (!localReviews.isNullOrEmpty()) {
                    localReviews.map { it.toEntity() }
                } else {
                    val remoteReviewsResponse =
                        movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
                    val remoteReviews =
                        remoteReviewsResponse.results?.map { it.toEntity() } ?: emptyList()

                    if (remoteReviews.isNotEmpty()) {
                        movieReviewLocalDataSource.addReview(remoteReviews.map { it.toLocalDto() })
                    }

                    remoteReviews
                }
            }
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

}