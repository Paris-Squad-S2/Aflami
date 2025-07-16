package com.example.movie.repository

import com.domain.mediaDetails.exception.NoMovieDetailsFoundException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository
import com.example.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.domain.mediaDetails.exception.RequestTimeoutException
import com.domain.mediaDetails.exception.ServerException
import com.domain.mediaDetails.exception.UnauthorizedException
import com.domain.mediaDetails.exception.UnknownException
import com.example.movie.dataSource.local.CastLocalDataSource
import com.example.movie.dataSource.local.GalleryLocalDataSource
import com.example.movie.dataSource.local.MovieLocalDataSource
import com.example.movie.dataSource.local.ReviewLocalDataSource
import com.example.movie.mapper.toEntity
import com.example.movie.mapper.toLocalDto
import com.example.movie.models.local.GalleryEntity
import com.example.movie.util.detectLanguage
import com.example.movie.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class MovieRepositoryImpl(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val castLocalDataSource: CastLocalDataSource,
    private val galleryLocalDataSource: GalleryLocalDataSource,
    private val reviewLocalDataSource: ReviewLocalDataSource,
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return safeCall {
            val localMovie = movieLocalDataSource.getMovie(movieId)
            val lastUpdate = movieLocalDataSource.getMovie(movieId)?.movieCacheDate
            lastUpdate?.let {
                if (localMovie == null && !isDataFresh(it)) {
                    val movieRemote =
                        movieDetailsRemoteDataSource.getMovieDetails(movieId, language).toLocalDto()
                    movieLocalDataSource.addMovie(movieRemote)
                }
            }

            localMovie?.toEntity()
                ?: throw NoMovieDetailsFoundException()
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return safeCall {
            val localCast = castLocalDataSource.getCastByMovieId(movieId)
            val castFilter = localCast.filter {
                !isDataFresh(it.castCacheDate)
            }
            if (castFilter.isEmpty()) {
                val movieCast =
                    movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                        .cast?.map { it.toEntity() } ?: emptyList()
                castLocalDataSource.addCast(movieCast.map { it.toLocalDto() })
            }
            localCast.map { it.toEntity() }
        }
    }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<Movie> {
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
            val localGallery = galleryLocalDataSource.getGalleryByMovieId(movieId)
            val lastUpdate = galleryLocalDataSource.getGalleryByMovieId(movieId)?.galleryCacheDate
           lastUpdate?.let {
               if (localGallery == null && !isDataFresh(it)) {
                   val movieImage =
                       movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity().images

                   galleryLocalDataSource.addGallery(
                       GalleryEntity(
                           movieId = movieId,
                           images = movieImage.map { it.toLocalDto() },
                           id = 0
                       )
                   )
               }
           }

            localGallery?.toEntity()
                ?: throw NoMovieDetailsFoundException()
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
            val localReview = reviewLocalDataSource.getReviewsForMovie(movieId)
            val reviewFilter = localReview.filter {
                !isDataFresh(it.reviewCacheDate)
            }
            if (reviewFilter.isEmpty()) {
                val movieCast =
                    movieDetailsRemoteDataSource.getMovieReviews(movieId,page, language)
                        .results?.map { it.toEntity() } ?: emptyList()
                reviewLocalDataSource.addReview(movieCast.map { it.toLocalDto() })
            }
            localReview.map { it.toEntity() }
        }
    }

    override suspend fun addMovieToFavorite(movieId: Int) {
        TODO("Not yet implemented")
    }

    private suspend fun <T> safeCall(call: suspend () -> T): T {
        return try {
            call()
        } catch (e: UnauthorizedException) {
            throw e
        } catch (e: RequestTimeoutException) {
            throw e
        } catch (e: ServerException) {
            throw e
        } catch (e: UnknownException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun isDataFresh(date: LocalDateTime): Boolean {
        val timeZone = TimeZone.currentSystemDefault()
        return date.toInstant(timeZone)
            .plus(1, DateTimeUnit.HOUR) >= getCurrentDate().toInstant(timeZone)
    }
}

