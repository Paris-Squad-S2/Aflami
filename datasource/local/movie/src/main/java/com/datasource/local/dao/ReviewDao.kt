package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.movie.models.local.ReviewEntity

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReviews(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM Reviews_Table WHERE movieId = :movieId")
    suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
}