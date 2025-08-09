package com.datasource.local.media.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.media.dao.HomeMediaDao
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.datasource.local.workmanager.ClearMediaWorker
import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity
import java.util.concurrent.TimeUnit

class HomeMediaLocalDataSourceImpl(
    private val homeMediaDao: HomeMediaDao,
    private val workManager: WorkManager,
) : HomeMediaLocalDataSource {
    override suspend fun addHomeMedia(mediaList: List<HomeMediaEntity>) {
        homeMediaDao.addHomeMedia(mediaList)
        mediaList.map { it.category }.distinct().forEach { category ->
            scheduleClearHomeMediaByCategory(category)
        }
    }

    override suspend fun getHomeMediaByCategory(
        category: Category,
        language: String,
    ): List<HomeMediaEntity> {
        return homeMediaDao.getHomeMediaByCategory(category = category, language = language)
    }

    override suspend fun clearHomeMediaByCategory(category: Category) {
        homeMediaDao.clearHomeMediaByCategory(category = category)
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