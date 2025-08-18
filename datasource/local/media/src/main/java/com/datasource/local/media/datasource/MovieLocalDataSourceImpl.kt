package com.datasource.local.media.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.media.dao.MovieDao
import com.repository.media.datasource.local.MovieLocalDataSource
import com.repository.media.datasource.local.workmanager.ClearMovieDetailWorker
import com.repository.media.models.local.moive.MovieCastEntity
import com.repository.media.models.local.moive.MovieGalleryEntity
import com.repository.media.models.local.moive.MovieEntity
import com.repository.media.models.local.moive.MovieSimilarEntity
import com.repository.media.models.local.moive.MovieReviewEntity
import java.util.concurrent.TimeUnit

class MovieLocalDataSourceImpl(
    private val workManager: WorkManager,
    private val movieDao: MovieDao
) : MovieLocalDataSource {
    override suspend fun addMovie(movie: MovieEntity) {
        movieDao.addMovies(movie)
        scheduleClearMovieWork(movie.id, movie.language)
    }

    override suspend fun getMovieById(movieId: Int, language: String): MovieEntity? =
        movieDao.getMovieById(movieId, language)

    override suspend fun clearMovieById(movieId: Int, language: String) =
        movieDao.clearMovieDetailsById(movieId, language)

    override suspend fun addMovieCast(cast: List<MovieCastEntity>) = movieDao.addMovieCast(cast)

    override suspend fun getCastByMovieId(movieId: Int, language: String): List<MovieCastEntity> =
        movieDao.getCastByMovieId(movieId, language)

    override suspend fun addMovieGallery(gallery: MovieGalleryEntity) = movieDao.addMovieGallery(gallery)
    override suspend fun getGalleryByMovieId(movieId: Int): MovieGalleryEntity? =
        movieDao.getGalleryByMovieId(movieId)

    override suspend fun addMovieReviews(reviews: List<MovieReviewEntity>) =
        movieDao.addMovieReviews(reviews)

    override suspend fun getReviewsByMovieId(movieId: Int, language: String): List<MovieReviewEntity> =
        movieDao.getReviewsByMovieId(movieId, language)

    override suspend fun addSimilarMovies(movieSimilar: List<MovieSimilarEntity>) =
        movieDao.addSimilarMovies(movieSimilar)

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
        language: String
    ): List<MovieSimilarEntity> = movieDao.getSimilarMovies(movieId, page, language)

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