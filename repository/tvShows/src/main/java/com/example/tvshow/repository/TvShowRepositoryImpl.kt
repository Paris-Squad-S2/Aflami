package com.example.tvshow.repository

import com.domain.mediaDetails.exception.RequestTimeoutException
import com.domain.mediaDetails.exception.ServerException
import com.domain.mediaDetails.exception.UnauthorizedException
import com.domain.mediaDetails.exception.UnknownException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.repository.TvShowRepository
import com.example.tvshow.dataSource.remote.TvShowDetailsRemoteDataSource
import com.example.tvshow.mapper.toEntity
import com.example.tvshow.util.detectLanguage

class TvShowRepositoryImpl(
    // inject here local and remote data source dependency
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
) : TvShowRepository {

    private val language = detectLanguage()

    override suspend fun getTvShowDetails(tvShowId: Int): TvShow {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
                .toEntity()
        }
    }

    override suspend fun getTvShowCast(tvShowId: Int): List<Cast> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
                .cast
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShow> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
                .tvShowSimilarDto
                ?.map { it.toEntity() }
                ?: emptyList()
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
                .productionCompanies
                ?.map { it.toEntity() } ?: emptyList()
        }
    }

    override suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season {
        return safeCall {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
                .toEntity()
        }
    }

    override suspend fun getTvShowReview(tvShowId: Int, page: Int): List<Review> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
                .results
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun addTvShowToFavorite(tvShowId: Int) {
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