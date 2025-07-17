package com.repository.dataSource.local

import com.repository.model.local.ReviewEntity

interface TvShowReviewLocalDataSource {
    suspend fun addReview(reviews: List<ReviewEntity>)
    suspend fun getReviewsByTvShowId(tvShowId: Int): List<ReviewEntity>
}