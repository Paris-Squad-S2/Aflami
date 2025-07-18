package com.datasource.local.datasource

import com.datasource.local.dao.MovieReviewDao
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.models.local.ReviewEntity

class MovieMovieReviewLocalDataSourceImpl(private val dao: MovieReviewDao) : MovieReviewLocalDataSource {
    override suspend fun addReview(reviews: List<ReviewEntity>) = dao.addReviews(reviews)

    override suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity> =
        dao.getReviewsByMovieId(movieId)
}