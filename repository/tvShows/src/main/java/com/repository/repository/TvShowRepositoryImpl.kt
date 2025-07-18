package com.repository.repository

import com.domain.mediaDetails.exception.NoGalleryTvShowFoundException
import com.domain.mediaDetails.exception.NoSeasonDetailsFoundException
import com.domain.mediaDetails.exception.NoTvShowDetailsFoundException
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
import com.domain.mediaDetails.model.TvShowSimilar
import com.domain.mediaDetails.repository.TvShowRepository
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toEntity
import com.repository.mapper.toLocalDto
import com.repository.model.local.GalleryEntity
import com.repository.util.detectLanguage
import com.repository.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class TvShowRepositoryImpl(
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowSeasonLocalDataSource: TvShowSeasonLocalDataSource,
    private val tvShowReviewLocalDataSource: TvShowReviewLocalDataSource,
    private val tvShowGalleryLocalDataSource: TvShowGalleryLocalDataSource,
    private val tvShowCastLocalDataSource: TvShowCastLocalDataSource,
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
) : TvShowRepository {

    private val language = detectLanguage()

    override suspend fun getTvShowDetails(tvShowId: Int): TvShow {
        return safeCall {
            val tvShowRemote = tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)

            tvShowLocalDataSource.getTvShowId(tvShowId)
                .takeIf { it != null && isDataFresh(it.tvShowCacheDate) }
                .also { tvShowLocalDataSource.addTvShow(tvShowRemote.toLocalDto()) }
                ?.toEntity()
                ?: throw NoTvShowDetailsFoundException()

        }
    }

    override suspend fun getTvShowCast(tvShowId: Int): List<Cast> {
        return safeCall {
            val movieCast =
                tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
                    .cast?.map { it.toEntity() } ?: emptyList()

            tvShowCastLocalDataSource.getCastByTvShowId(tvShowId)
                .filter { isDataFresh(it.castCacheDate) }
                .takeIf { it.isNotEmpty() }
                .also { tvShowCastLocalDataSource.addCast(movieCast.map { it.toLocalDto() }) }
                ?.map { it.toEntity() } ?: emptyList()

        }
    }

    override suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return safeCall {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
                .tvShowSimilarDto
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun getTvShowGallery(tvShowId: Int): Gallery {
        return safeCall {
            val movieImage =
                tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId).toEntity().images

            tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)
                .takeIf { it != null && isDataFresh(it.galleryCacheDate) }
                .also {
                    tvShowGalleryLocalDataSource.addGallery(
                        GalleryEntity(
                            tvShowId = tvShowId,
                            images = movieImage.map { it.toLocalDto() },
                            id = 0
                        )
                    )
                }?.toEntity()
                ?: throw NoGalleryTvShowFoundException()

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
            val seasonRemote = tvShowDetailsRemoteDataSource.getSeasonDetails(
                tvShowId,
                seasonNumber,
                language
            )

            tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
                .takeIf { it != null && isDataFresh(it.seasonCacheDate) }
                .also { tvShowSeasonLocalDataSource.addSeasonDetails(seasonRemote.toLocalDto()) }
                ?.toEntity()
                ?: throw NoSeasonDetailsFoundException()

        }
    }

    override suspend fun getTvShowReview(tvShowId: Int, page: Int): List<Review> {
        return safeCall {
            val movieCast =
                tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
                    .results?.map { it.toEntity() } ?: emptyList()

            tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId)
                .filter { isDataFresh(it.reviewCacheDate) }
                .takeIf { it.isNotEmpty() }
                .also { tvShowReviewLocalDataSource.addReview(movieCast.map { it.toLocalDto() }) }
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