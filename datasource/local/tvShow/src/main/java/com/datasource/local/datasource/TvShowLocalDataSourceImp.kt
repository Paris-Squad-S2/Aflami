package com.datasource.local.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.dao.TvShowDao
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.workmanager.ClearTvShowDetailsWorker
import com.repository.model.local.TvShowEntity
import java.util.concurrent.TimeUnit

class TvShowLocalDataSourceImp (
    private val workManager: WorkManager,
    private val dao: TvShowDao
) : TvShowLocalDataSource {
    override suspend fun addTvShow(tvShow: TvShowEntity) {
        dao.addTvShow(tvShow)
        scheduleClearTvShowWork(tvShow.id, tvShow.language)
    }

    override suspend fun getTvShowId(tvShowId: Int, language: String): TvShowEntity? =
        dao.getTvShowById(tvShowId, language)

    override suspend fun clearTvShowById(tvShowId: Int, language: String) =
        dao.clearTVShowDetailsById(tvShowId, language)

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