package com.repository.repository

import com.domain.mediaDetails.exception.NetworkException
import com.domain.mediaDetails.exception.NoFoundTvShowException
import com.domain.mediaDetails.exception.NoFundGalleryTvShowException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.exception.NoSeasonFoundException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.ProductionCompany
import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.model.TvShowSimilar
import com.domain.mediaDetails.model.TvShowVideo
import com.domain.mediaDetails.repository.TvShowRepository
import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toEntity
import com.repository.mapper.toLocalDto
import com.repository.util.NetworkConnectionChecker
import com.repository.util.detectLanguage

class TvShowRepositoryImpl(
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
    private val tvShowCastLocalDataSource: TvShowCastLocalDataSource,
    private val tvShowGalleryLocalDataSource: TvShowGalleryLocalDataSource,
    private val tvShowReviewLocalDataSource: TvShowReviewLocalDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowSeasonLocalDataSource: TvShowSeasonLocalDataSource,
    private val tvShowSimilarLocalDataSource: TvShowSimilarLocalDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
) : TvShowRepository {

    private val language = detectLanguage()

    override suspend fun getTvShowDetails(tvShowId: Int): TvShow {
        return safeCall {
            val localTVShow = tvShowLocalDataSource.getTvShowId(tvShowId, language)
            if (localTVShow != null) {
                localTVShow.toEntity()
            } else {
                val remoteTvShow =
                    tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
                tvShowLocalDataSource.addTvShow(remoteTvShow.toLocalDto(language, tvShowId))
                tvShowLocalDataSource.getTvShowId(tvShowId, language)?.toEntity()
                    ?: throw NoFoundTvShowException()
            }

        }
    }

    override suspend fun getTvShowCast(tvShowId: Int): List<Cast> {
        return safeCall {
            val localCast = tvShowCastLocalDataSource.getCastByTvShowId(tvShowId, language)
            if (localCast.isNotEmpty()) {
                localCast.map { it.toEntity() }
            } else {
                val remoteCast = tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
                    .cast ?: emptyList()

                tvShowCastLocalDataSource.addCast(
                    remoteCast.map { it.toLocalDto(language, tvShowId) }
                )

                tvShowCastLocalDataSource.getCastByTvShowId(tvShowId, language)
                    .map { it.toEntity() }
            }

        }
    }

    override suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShowSimilar> {
        return safeCall {
            val localSimilar =
                tvShowSimilarLocalDataSource.getSimilarTvShows(tvShowId, page, language)
            if (localSimilar.isNotEmpty()) {
                localSimilar.map { it.toEntity() }
            } else {
                val remoteSimilar =
                    tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
                        .tvShowSimilarDto ?: emptyList()

                tvShowSimilarLocalDataSource.addSimilarTvShows(remoteSimilar.map {
                    it.toLocalDto(
                        tvShowId,
                        language,
                        page
                    )
                })
                tvShowSimilarLocalDataSource.getSimilarTvShows(tvShowId, page, language)
                    .map { it.toEntity() }
            }
        }
    }

    override suspend fun getTvShowGallery(tvShowId: Int): Gallery {
        return safeCall {
            val localGallery = tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)
            if (localGallery != null) {
                localGallery.toEntity()
            } else {
                val remoteGallery = tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
                tvShowGalleryLocalDataSource.addGallery(remoteGallery.toLocalDto(tvShowId))
                tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)?.toEntity()
                    ?: throw NoFundGalleryTvShowException()
            }
        }
    }

    override suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany> {
        return safeCall {
            val localCompany = tvShowLocalDataSource.getTvShowId(tvShowId, language)
                ?.productionCompanies ?: emptyList()

            if (localCompany.isNotEmpty()) {
                localCompany.map { it.toEntity() }
            } else {
                val remoteCompany = tvShowDetailsRemoteDataSource.getTvShowDetails(
                    tvShowId,
                    language
                )
                tvShowLocalDataSource.addTvShow(remoteCompany.toLocalDto(language, tvShowId))
                tvShowLocalDataSource.getTvShowId(tvShowId, language)
                    ?.productionCompanies?.map { it.toEntity() } ?: emptyList()

            }
        }
    }

    override suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season {
        return safeCall {
            val localSeason = tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
            if (localSeason != null) {
                localSeason.toEntity()
            } else {
                val remoteSeason = tvShowDetailsRemoteDataSource.getSeasonDetails(
                    tvShowId,
                    seasonNumber,
                    language
                )
                tvShowSeasonLocalDataSource.addSeasonDetails(remoteSeason.toLocalDto(tvShowId))
                tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)?.toEntity()
                    ?: throw NoSeasonFoundException()
            }

        }
    }

    override suspend fun getTvShowReview(tvShowId: Int, page: Int): List<Review> {
        return safeCall {
            val localReview = tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
            if (localReview.isNotEmpty()) {
                localReview.map { it.toEntity() }
            } else {
                val remoteReview =
                    tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
                tvShowReviewLocalDataSource.addReview(remoteReview.results?.map {
                    it.toLocalDto(
                        tvShowId,
                        language
                    )
                } ?: emptyList())
                tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
                    .map { it.toEntity() }
            }

        }
    }

    override suspend fun addTvShowToFavorite(tvShowId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrailerVideoForTvShow(tvShowId: Int): List<TvShowVideo> {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return safeCall {
            tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId)
                .tvShowVideoResultDto
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    private suspend fun <T> safeCall(call: suspend () -> T): T {
        return try {
            if (networkConnectionChecker.isConnected.value.not()) {
                throw NoInternetConnectionException()
            }
            call()
        } catch (_: NoInternetConnectionException) {
            throw NoInternetConnectionException()
        } catch (_: NoFoundTvShowException) {
            throw NoFoundTvShowException()
        } catch (_: NoFundGalleryTvShowException) {
            throw NoFundGalleryTvShowException()
        } catch (_: NoSeasonFoundException) {
            throw NoSeasonFoundException()
        } catch (e: Exception) {
            throw NetworkException(e.message ?: "Unknown error")
        }
    }
}