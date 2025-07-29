package com.repository.movie.dataSource.local.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.repository.movie.dataSource.local.MovieLocalDataSource

class ClearMovieDetailWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val movieLocalDataSource: MovieLocalDataSource
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val movieId = inputData.getInt(MOVIE_ID, -1)
            val language = inputData.getString(LANGUAGE) ?: run {
                return Result.failure()
            }

            if (movieId == -1) return Result.failure()

            movieLocalDataSource.clearMovieById(movieId, language)
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val LANGUAGE = "language"

    }
}