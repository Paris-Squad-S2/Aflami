package com.repository.search.dataSource.local.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.entity.SearchType

class ClearMediaWorker(
    context: Context,
    workerParams: WorkerParameters,
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
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object{
        const val SEARCH_QUERY = "search_query"
        const val SEARCH_TYPE = "search_type"

    }

}