package com.example.movie.dataSource.local

import com.example.movie.models.local.ReviewEntity

interface ReviewLocalDataSource {
    suspend fun addReview(reviews: List<ReviewEntity>)
    suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity>
}