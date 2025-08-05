package com.repository.media.datasource.local.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.repository.media.datasource.local.MediaLocalDataSource
import com.repository.media.entity.SearchType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ClearMediaWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val mediaLocalDataSource: MediaLocalDataSource
) : CoroutineWorker(context, workerParams){


    override suspend fun doWork(): Result {
        return try {
            val searchQuery = inputData.getString(SEARCH_QUERY) ?: run {
                return Result.failure()
            }
            val searchTypeString = inputData.getString(SEARCH_TYPE) ?: run {
                return Result.failure()
            }
            val searchType = SearchType.valueOf(searchTypeString)

            mediaLocalDataSource.clearAllMediaBySearchQuery(searchQuery, searchType)
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }

    companion object{
        const val SEARCH_QUERY = "search_query"
        const val SEARCH_TYPE = "search_type"

    }

}