package com.example.movie.repository

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
import com.example.movie.mapper.toEntity
import com.example.movie.util.detectLanguage

class MovieRepositoryImpl(
    // inject here local and remote data source dependency
    private val movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource,
) : MovieRepository {
    private val language = detectLanguage()

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return safeCall {
            movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
                .toEntity()
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<Cast> {
        return safeCall {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            ).cast?.map { it.toEntity() } ?: emptyList()
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
            movieDetailsRemoteDataSource.getMovieImages(movieId).toEntity()
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
            ).results
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
        } catch (e: Exception){
            throw e
        }
    }
}

