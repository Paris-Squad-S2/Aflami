package com.repository.movie.repository

import com.domain.mediaDetails.exception.NoGalleryMovieDetailsFoundException
import com.domain.mediaDetails.exception.NoMovieDetailsFoundException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Movie
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.exception.RequestTimeoutException
import com.domain.mediaDetails.exception.ServerException
import com.domain.mediaDetails.exception.UnauthorizedException
import com.domain.mediaDetails.exception.UnknownException
import com.repository.movie.dataSource.local.CastLocalDataSource
import com.repository.movie.dataSource.local.GalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.ReviewLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.mapper.toLocalDto
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.util.detectLanguage
import com.repository.movie.util.getCurrentDate
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
            val movieRemote =
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)

            movieLocalDataSource.getMovie(movieId)
                .takeIf { it != null && isDataFresh(it.movieCacheDate) }
                .also { movieLocalDataSource.addMovie(movieRemote.toLocalDto()) }
                ?.toEntity()
                ?: throw NoMovieDetailsFoundException()
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return safeCall {
            val movieCast =
                movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
                    .cast?.map { it.toEntity() } ?: emptyList()

            castLocalDataSource.getCastByMovieId(movieId)
                .filter { isDataFresh(it.castCacheDate) }
                .takeIf { it.isNotEmpty() }
                .also { castLocalDataSource.addCast(movieCast.map { it.toLocalDto() }) }
                ?.map { it.toEntity() }
                ?: emptyList()
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
            val movieImage =
                movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity().images

            galleryLocalDataSource.getGalleryByMovieId(movieId)
                .takeIf { it != null && isDataFresh(it.galleryCacheDate) }
                .also {
                    galleryLocalDataSource.addGallery(
                        GalleryEntity(
                            movieId = movieId,
                            images = movieImage.map { it.toLocalDto() },
                            id = 0
                        )
                    )
                }
                ?.toEntity()
                ?: throw NoGalleryMovieDetailsFoundException()
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
            val movieCast =
                movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
                    .results?.map { it.toEntity() } ?: emptyList()

            reviewLocalDataSource.getReviewsForMovie(movieId)
                .filter { isDataFresh(it.reviewCacheDate) }
                .takeIf { it.isNotEmpty() }
                .also { reviewLocalDataSource.addReview(movieCast.map { it.toLocalDto() }) }
                ?.map { it.toEntity() }
                ?: emptyList()
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

