package com.datasource.local.search.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.search.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchQuery(history: SearchHistoryEntity)

    @Query("SELECT * FROM search_history_table ORDER BY id DESC")
    suspend fun getAllSearchQueries(): List<SearchHistoryEntity>

    @Query("DELETE FROM search_history_table WHERE id = :historyId")
    suspend fun clearSearchQueryById(historyId: Long)

    @Query("DELETE FROM search_history_table")
    suspend fun clearAllSearchQueries()

}