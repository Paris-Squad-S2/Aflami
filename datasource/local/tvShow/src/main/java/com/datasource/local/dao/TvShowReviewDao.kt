package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.ReviewEntity

@Dao
interface TvShowReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReviews(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM reviews_table WHERE tvShowId = :tvShowId")
    suspend fun getReviewsByTvShowId(tvShowId: Int): List<ReviewEntity>
}