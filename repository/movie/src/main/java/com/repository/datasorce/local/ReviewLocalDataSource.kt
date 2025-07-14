package com.repository.datasorce.local

import com.repository.entity.ReviewEntity

interface ReviewLocalDataSource {
    suspend fun addReview(reviews: List<ReviewEntity>)
    suspend fun getReviews(): List<ReviewEntity>
}