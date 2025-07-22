package com.repository.movie.repository

import com.paris_2.domain.movie.exception.NetworkException
import com.paris_2.domain.movie.exception.NoFoundMovieException
import com.paris_2.domain.movie.exception.NoFundGalleryMovieException
import com.paris_2.domain.movie.exception.NoInternetConnectionException
import com.paris_2.domain.movie.model.MovieCast
import com.paris_2.domain.movie.model.MovieGallery
import com.paris_2.domain.movie.model.Movie
import com.paris_2.domain.movie.model.MovieSimilar
import com.paris_2.domain.movie.model.MovieProductionCompany
import com.paris_2.domain.movie.model.MovieReview
import com.paris_2.domain.movie.repository.MovieRepository
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
    private val movieSimilarLocalDataSource: MovieSimilarLocalDataSource
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return safeCall {
            val localMovie = movieLocalDataSource.getMovieById(movieId, language)

            if (localMovie != null) {
                localMovie.toEntity()
            } else {
                val remoteMovie = movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
                movieLocalDataSource.addMovie(remoteMovie.toLocalDto(language))
                movieLocalDataSource.getMovieById(movieId, language)?.toEntity()
                    ?: throw NoFoundMovieException()
            }
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<MovieCast> {
        return safeCall {


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
        return safeCall {

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

    override suspend fun getMovieGallery(movieId: Int): MovieGallery {
        return safeCall {
            val localGallery = movieGalleryLocalDataSource.getGalleryByMovieId(movieId)

            if (localGallery != null) {
                localGallery.toEntity()
            } else {
                val remoteGallery = movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity()
                val remoteGalleryImages = remoteGallery.movieImages
                movieGalleryLocalDataSource.addGallery(
                    GalleryEntity(
                        movieId = movieId,
                        images = remoteGalleryImages.map { it.toLocalDto() },
                    )
                )
                movieGalleryLocalDataSource.getGalleryByMovieId(movieId)?.toEntity()
                    ?: throw NoFundGalleryMovieException()
            }
        }
    }

    override suspend fun getCompanyProducts(movieId: Int): List<MovieProductionCompany> {
        return safeCall {
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

    override suspend fun getMovieReview(movieId: Int, page: Int): List<MovieReview> {
        return safeCall {

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