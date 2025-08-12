package com.datasource.local.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.dao.MovieDao
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.workmanager.ClearMovieDetailWorker
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.ReviewEntity
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

    override suspend fun addMovieCast(cast: List<CastEntity>) = movieDao.addMovieCast(cast)

    override suspend fun getCastByMovieId(movieId: Int, language: String): List<CastEntity> =
        movieDao.getCastByMovieId(movieId, language)

    override suspend fun addMovieGallery(gallery: GalleryEntity) = movieDao.addMovieGallery(gallery)
    override suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity? =
        movieDao.getGalleryByMovieId(movieId)

    override suspend fun addMovieReviews(reviews: List<ReviewEntity>) =
        movieDao.addMovieReviews(reviews)

    override suspend fun getReviewsByMovieId(movieId: Int, language: String): List<ReviewEntity> =
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