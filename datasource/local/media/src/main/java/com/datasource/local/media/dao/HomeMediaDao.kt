package com.datasource.local.media.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity
import org.intellij.lang.annotations.Language


@Dao
interface HomeMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMediaList(media: List<HomeMediaEntity>)

    @Query("SELECT * FROM home_media_table WHERE category = :category AND language = :language")
    suspend fun getMediaListByCategory(category: Category,language: String): List<HomeMediaEntity>

    @Query("DELETE FROM home_media_table WHERE category = :category")
    suspend fun clearMediaByCategory(category: Category)
}