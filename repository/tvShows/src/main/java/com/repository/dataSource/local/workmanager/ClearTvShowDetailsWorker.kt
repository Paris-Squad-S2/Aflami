package com.repository.dataSource.local.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.repository.dataSource.local.TvShowLocalDataSource

class ClearTvShowDetailsWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val tvShowLocalDataSource: TvShowLocalDataSource
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val movieId = inputData.getInt(TV_SHOW_ID, -1)
            val language = inputData.getString(LANGUAGE) ?: run {
                return Result.failure()
            }

            if (movieId == -1) return Result.failure()

            tvShowLocalDataSource.clearTvShowById(movieId, language)
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val TV_SHOW_ID = "tv_show_id"
        const val LANGUAGE = "language"

    }
}