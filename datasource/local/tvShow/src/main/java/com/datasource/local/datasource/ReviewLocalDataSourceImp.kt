package com.datasource.local.datasource

import com.datasource.local.dao.ReviewDao
import com.repository.datasource.local.ReviewLocalDataSource
import com.repository.entity.ReviewEntity

class ReviewLocalDataSourceImp(private val dao: ReviewDao) : ReviewLocalDataSource {
    override suspend fun addReview(reviews: List<ReviewEntity>) = dao.addReviews(reviews)

    override suspend fun getReviewsByTvShowId(tvShowId: Int): List<ReviewEntity> =
        dao.getReviewsByTvShowId(tvShowId)
}