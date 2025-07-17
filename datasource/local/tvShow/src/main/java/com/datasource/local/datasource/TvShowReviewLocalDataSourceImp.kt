package com.datasource.local.datasource

import com.datasource.local.dao.TvShowReviewDao
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.model.local.ReviewEntity

class TvShowReviewLocalDataSourceImp(private val dao: TvShowReviewDao) : TvShowReviewLocalDataSource {
    override suspend fun addReview(reviews: List<ReviewEntity>) = dao.addReviews(reviews)

    override suspend fun getReviewsByTvShowId(tvShowId: Int): List<ReviewEntity> =
        dao.getReviewsByTvShowId(tvShowId)
}