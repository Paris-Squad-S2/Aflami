package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.movie.models.local.ReviewEntity

@Dao
interface MovieReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReviews(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM reviews_table WHERE movieId = :movieId And language = :language")
    suspend fun getReviewsByMovieId(movieId: Int,language: String): List<ReviewEntity>?
}