package com.datasource.local.media.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.media.dao.MediaDao
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.datasource.local.workmanager.ClearMediaWorker
import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.HomeMediaEntity
import com.repository.media.models.local.media.MediaEntity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class MediaLocalDataSourceImpl(
    private val mediaDao: MediaDao,
    private val workManager: WorkManager,
) : MediaLocalDataSource {
    override suspend fun addHomeMedia(mediaList: List<HomeMediaEntity>) {
        mediaDao.addHomeMedia(mediaList)
        mediaList.map { it.category }.distinct().forEach { category ->
            scheduleClearHomeMediaByCategory(category)
        }
    }

    override suspend fun getHomeMediaByCategory(
        category: Category,
        language: String,
    ): List<HomeMediaEntity> {
        return mediaDao.getHomeMediaByCategory(category = category, language = language)
    }

    override suspend fun clearHomeMediaByCategory(category: Category) {
        mediaDao.clearHomeMediaByCategory(category = category)
    }

    override suspend fun addMediaContinueWatching(media: MediaEntity) {
        mediaDao.addMediaContinueWatching(media)
    }

    override fun getMediaContinueWatching(): Flow<List<MediaEntity>> {
        return mediaDao.getMediaContinueWatching()
    }

    private fun scheduleClearHomeMediaByCategory(category: Category) {
        val inputData = workDataOf(
            ClearMediaWorker.CATEGORY_KEY to category.name
        )

        val workRequest = OneTimeWorkRequestBuilder<ClearMediaWorker>()
            .setInputData(inputData)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        workManager.enqueue(workRequest)
    }
}