package com.repository.repository

import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.entity.MediaVideo
import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.entity.Season
import com.paris_2.domain.media.entity.TvShow
import com.paris_2.domain.media.entity.TvShowSimilar
import com.paris_2.domain.media.entity.TvShowVideo
import com.paris_2.domain.media.exception.AflamiException
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toEntity
import com.repository.mapper.toLocalDto
import com.repository.util.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class TvShowRepositoryImpl(
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val settingLocalDataSource: SettingLocalDataSource,
) : TvShowRepository {

    override suspend fun getTvShowDetails(tvShowId: Int): TvShow {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getTvShowDetails")) {
            val localTVShow = tvShowLocalDataSource.getTvShowId(tvShowId, language)
            if (localTVShow != null) {
                localTVShow.toEntity()
            } else {
                val remoteTvShow =
                    tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
                tvShowLocalDataSource.addTvShow(remoteTvShow.toLocalDto(language, tvShowId))
                tvShowLocalDataSource.getTvShowId(tvShowId, language)?.toEntity()
                    ?: throw FailedException("getTvShowDetails")
            }

        }
    }

    override suspend fun getTvShowCast(tvShowId: Int): List<Cast> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getTvShowCast")) {
            val localCast = tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
            if (localCast.isNotEmpty()) {
                localCast.map { it.toEntity() }
            } else {
                val remoteCast = tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
                    .cast ?: emptyList()

                tvShowLocalDataSource.addTvShowCast(
                    remoteCast.map { it.toLocalDto(language, tvShowId) }
                )

                tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
                    .map { it.toEntity() }
            }

        }
    }

    override suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShowSimilar> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getTvShowRecommendations")) {
            val localSimilar =
                tvShowLocalDataSource.getSimilarTvShows(tvShowId, page, language)
            if (localSimilar.isNotEmpty()) {
                localSimilar.map { it.toEntity() }
            } else {
                val remoteSimilar =
                    tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
                        .tvShowSimilarDto ?: emptyList()

                tvShowLocalDataSource.addSimilarTvShows(remoteSimilar.map {
                    it.toLocalDto(
                        tvShowId,
                        language,
                        page
                    )
                })
                tvShowLocalDataSource.getSimilarTvShows(tvShowId, page, language)
                    .map { it.toEntity() }
            }
        }
    }

    override suspend fun getTvShowGallery(tvShowId: Int): List<Image> {
        return safeCall(FailedException("getTvShowGallery")) {
            val localGallery = tvShowLocalDataSource.getGalleryByTvShowId(tvShowId)
            if (localGallery != null) {
                localGallery.toEntity()
            } else {
                val remoteGallery = tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
                tvShowLocalDataSource.addTvShowGallery(remoteGallery.toLocalDto(tvShowId))
                tvShowLocalDataSource.getGalleryByTvShowId(tvShowId)?.toEntity()
                    ?: throw FailedException("getTvShowGallery")
            }
        }
    }

    override suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getCompanyProducts")) {
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
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getSeasonDetails")) {
            val localSeason = tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                tvShowId,
                seasonNumber
            )
            if (localSeason != null) {
                localSeason.toEntity()
            } else {
                val remoteSeason = tvShowDetailsRemoteDataSource.getSeasonDetails(
                    tvShowId,
                    seasonNumber,
                    language
                )
                tvShowLocalDataSource.addTvShowSeason(remoteSeason.toLocalDto(tvShowId))
                tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )?.toEntity() ?: throw FailedException("getSeasonDetails")
            }

        }
    }

    override suspend fun getTvShowReview(tvShowId: Int, page: Int): List<Review> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("getTvShowReview")) {
            val localReview = tvShowLocalDataSource.getReviewsByTvShowId(tvShowId, language)
            if (localReview.isNotEmpty()) {
                localReview.map { it.toEntity() }
            } else {
                val remoteReview =
                    tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
                tvShowLocalDataSource.addTvShowReviews(remoteReview.results?.map {
                    it.toLocalDto(
                        tvShowId,
                        language
                    )
                } ?: emptyList())
                tvShowLocalDataSource.getReviewsByTvShowId(tvShowId, language)
                    .map { it.toEntity() }
            }

        }
    }

    override suspend fun addRatingToTvShow(movieId: Int, rating: Float) {
        return safeCall(FailedException("addRatingToTvShow")) {
            tvShowDetailsRemoteDataSource.addRatingToTvShow(
                movieId = movieId,
                rating = rating
            )
        }
    }

    override suspend fun deleteTvShowRating(tvShowId: Int) {
        return safeCall(FailedException("deleteTvShowRating")) {
            tvShowDetailsRemoteDataSource.deleteTvShowRating(
                tvShowId = tvShowId
            )
        }
    }


    override suspend fun getTrailerVideoForTvShow(tvShowId: Int): List<TvShowVideo> {
        return safeCall(FailedException("getTrailerVideoForTvShow")) {
            tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId)
                .tvShowVideoResultDto
                ?.map { it.toEntity() }
                ?: emptyList()
        }
    }

    override suspend fun getTrailerVideoForEpisode(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
    ): List<MediaVideo> {
        val language = settingLocalDataSource.getLanguage().first()
        return tvShowDetailsRemoteDataSource.getTrailerVideoForEpisode(
            tvShowId,
            seasonNumber,
            episodeNumber,
            language
        ).episodeVideoResultDto
            ?.map { it.toEntity() }
            ?: emptyList()
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