package com.repository.movie.dataSource.local

import com.repository.movie.models.local.ReviewEntity

interface ReviewLocalDataSource {
    suspend fun addReview(reviews: List<ReviewEntity>)
    suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity>
}