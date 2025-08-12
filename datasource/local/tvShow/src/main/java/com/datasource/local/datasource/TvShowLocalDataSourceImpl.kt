package com.datasource.local.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.dao.TvShowDao
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.workmanager.ClearTvShowDetailsWorker
import com.repository.model.local.CastEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity
import java.util.concurrent.TimeUnit

class TvShowLocalDataSourceImpl (
    private val workManager: WorkManager,
    private val tvShowDao: TvShowDao
) : TvShowLocalDataSource {
    override suspend fun addTvShow(tvShow: TvShowEntity) {
        tvShowDao.addTvShow(tvShow)
        scheduleClearTvShowWork(tvShow.id, tvShow.language)
    }

    override suspend fun getTvShowId(tvShowId: Int, language: String): TvShowEntity? =
        tvShowDao.getTvShowById(tvShowId, language)

    override suspend fun clearTvShowById(tvShowId: Int, language: String) =
        tvShowDao.clearTVShowDetailsById(tvShowId, language)

    override suspend fun addTvShowCast(cast: List<CastEntity>) = tvShowDao.addTvShowCast(cast)

    override suspend fun getCastByTvShowId(tvShowId: Int,language: String): List<CastEntity> =
        tvShowDao.getCastByTvShowId(tvShowId,language)
    override suspend fun addTvShowGallery(gallery: GalleryEntity) = tvShowDao.addTvShowGallery(gallery)

    override suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity? =
        tvShowDao.getGalleryByTvShowId(tvShowId)

    override suspend fun addTvShowReviews(reviews: List<ReviewEntity>) = tvShowDao.addTvShowReviews(reviews)

    override suspend fun getReviewsByTvShowId(tvShowId: Int,language: String): List<ReviewEntity> =
        tvShowDao.getReviewsByTvShowId(tvShowId,language)
    override suspend fun addTvShowSeason(seasons: SeasonEntity) = tvShowDao.addTvShowSeason(seasons)

    override suspend fun getSeasonByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity? =
        tvShowDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

    override suspend fun addSimilarTvShows(tvShowSimilar: List<TvShowSimilarEntity>) {
        tvShowDao.addSimilarTvShows(tvShowSimilar)
    }

    override suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity> {
        return tvShowDao.getSimilarTvShows(tvShowId,page,language)
    }

    private fun scheduleClearTvShowWork(tvShowId: Int, language: String) {
        val inputData = workDataOf(
            ClearTvShowDetailsWorker.TV_SHOW_ID to tvShowId,
            ClearTvShowDetailsWorker.LANGUAGE to language
        )

        val workRequest = OneTimeWorkRequestBuilder<ClearTvShowDetailsWorker>()
            .setInputData(inputData)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        workManager.enqueue(workRequest)
    }

}