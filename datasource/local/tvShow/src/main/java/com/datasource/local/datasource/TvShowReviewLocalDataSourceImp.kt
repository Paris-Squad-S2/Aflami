package com.datasource.local.datasource

import com.datasource.local.dao.TvShowReviewDao
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.model.local.ReviewEntity
import javax.inject.Inject

class TvShowReviewLocalDataSourceImp @Inject constructor(private val dao: TvShowReviewDao) : TvShowReviewLocalDataSource {
    override suspend fun addReview(reviews: List<ReviewEntity>) = dao.addReviews(reviews)

    override suspend fun getReviewsByTvShowId(tvShowId: Int,language: String): List<ReviewEntity> =
        dao.getReviewsByTvShowId(tvShowId,language)
}