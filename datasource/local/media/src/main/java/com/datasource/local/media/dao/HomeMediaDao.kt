package com.datasource.local.media.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity


@Dao
interface HomeMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHomeMedia(media: List<HomeMediaEntity>)

    @Query("SELECT * FROM home_media_table WHERE category = :category AND language = :language")
    suspend fun getHomeMediaByCategory(category: Category, language: String): List<HomeMediaEntity>

    @Query("DELETE FROM home_media_table WHERE category = :category")
    suspend fun clearHomeMediaByCategory(category: Category)
}