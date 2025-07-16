package com.datasource.local.datasource

import com.datasource.local.dao.ReviewDao
import com.example.movie.dataSource.local.ReviewLocalDataSource
import com.example.movie.models.local.ReviewEntity

class ReviewLocalDataSourceImp(private val dao: ReviewDao) : ReviewLocalDataSource {
    override suspend fun addReview(reviews: List<ReviewEntity>) = dao.addReviews(reviews)

    override suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity> =
        dao.getReviewsByMovieId(movieId)
}