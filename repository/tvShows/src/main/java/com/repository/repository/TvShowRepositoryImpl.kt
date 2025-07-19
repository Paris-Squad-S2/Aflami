package com.repository.repository

import com.domain.mediaDetails.exception.NetworkException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowSimilar
import com.domain.mediaDetails.repository.TvShowRepository
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toEntity
import com.repository.util.NetworkConnectionChecker
import com.repository.util.detectLanguage

class TvShowRepositoryImpl(
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
) : TvShowRepository {

    private val language = detectLanguage()

    override suspend fun getTvShowDetails(tvShowId: Int): TvShow {
        return safeCall {
            val tvShowRemote = tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
            tvShowRemote.toEntity()
        }
    }

    override suspend fun getTvShowCast(tvShowId: Int): List<Cast> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
                .cast?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
                .tvShowSimilarDto?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun getTvShowGallery(tvShowId: Int): Gallery {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
                .toEntity()
        }
    }

    override suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
                .productionCompanies?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season {
        return safeCall {
            val seasonRemote = tvShowDetailsRemoteDataSource.getSeasonDetails(
                tvShowId,
                seasonNumber,
                language
            )
            seasonRemote.toEntity()
        }
    }

    override suspend fun getTvShowReview(tvShowId: Int, page: Int): List<Review> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
                .results?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun addTvShowToFavorite(tvShowId: Int) {
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