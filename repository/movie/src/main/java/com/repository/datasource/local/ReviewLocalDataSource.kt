package com.repository.datasource.local

import com.repository.entity.ReviewEntity

interface ReviewLocalDataSource {
    suspend fun addReview(reviews: List<ReviewEntity>)
    suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity>
}