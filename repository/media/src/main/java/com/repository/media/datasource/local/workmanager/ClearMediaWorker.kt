package com.repository.media.datasource.local.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import androidx.hilt.work.HiltWorker
import com.repository.media.datasource.local.HomeMediaLocalDataSource
import com.repository.media.entity.Category


@HiltWorker
class ClearMediaWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val homeMediaLocalDataSource: HomeMediaLocalDataSource
) : CoroutineWorker(context, workerParams){

    override suspend fun doWork(): Result {
        return try {
            val categoryName = inputData.getString(CATEGORY_KEY) ?: return Result.failure()
            val category = Category.valueOf(categoryName)
            homeMediaLocalDataSource.clearHomeMediaByCategory(category)
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
    companion object{
        const val CATEGORY_KEY = "category_key"
    }
}