package com.datasource.local.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.dao.MovieDao
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.workmanager.ClearMovieDetailWorker
import com.repository.movie.models.local.MovieEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieLocalDataSourceImp @Inject constructor(
    private val workManager: WorkManager,
    private val dao: MovieDao
) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) {
        dao.addMovies(movie)
        scheduleClearMovieWork(movie.id, movie.language)
    }

    override suspend fun getMovieById(movieId: Int, language: String): MovieEntity? =
        dao.getMovieById(movieId, language)

    override suspend fun clearMovieById(movieId: Int, language: String) =
        dao.clearMovieDetailsById(movieId, language)

    private fun scheduleClearMovieWork(movieId: Int, language: String) {
        val inputData = workDataOf(
            ClearMovieDetailWorker.MOVIE_ID to movieId,
            ClearMovieDetailWorker.LANGUAGE to language
        )

        val workRequest = OneTimeWorkRequestBuilder<ClearMovieDetailWorker>()
            .setInputData(inputData)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        workManager.enqueue(workRequest)
    }

}