package com.datasource.local.datasource

import com.datasource.local.dao.TvShowCastReviewDao
import com.repository.dataSource.local.ReviewLocalDataSource
import com.repository.model.local.ReviewEntity

class TvShowReviewLocalDataSourceImp(private val dao: TvShowCastReviewDao) : ReviewLocalDataSource {
    override suspend fun addReview(reviews: List<ReviewEntity>) = dao.addReviews(reviews)

    override suspend fun getReviewsByTvShowId(tvShowId: Int): List<ReviewEntity> =
        dao.getReviewsByTvShowId(tvShowId)
}