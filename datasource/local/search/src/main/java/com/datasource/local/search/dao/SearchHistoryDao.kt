package com.datasource.local.search.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.search.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchQuery(history: SearchHistoryEntity)

    @Query("SELECT * FROM search_history_table ORDER BY search_query DESC")
    fun getAllSearchQueries(): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_history_table WHERE search_query = :searchQuery")
    suspend fun getSearchHistoryQuery(searchQuery: String): SearchHistoryEntity?

    @Query("DELETE FROM search_history_table WHERE search_query = :searchQuery")
    suspend fun clearSearchQueryByQuery(searchQuery: String)

    @Query("DELETE FROM search_history_table")
    suspend fun clearAllSearchQueries()

}