package com.repository.movie.dataSource.local

import com.repository.movie.models.local.ReviewEntity

interface MovieReviewLocalDataSource {
    suspend fun addMovieReviews(reviews: List<ReviewEntity>)
    suspend fun getReviewsByMovieId(movieId: Int, language: String): List<ReviewEntity>?
}