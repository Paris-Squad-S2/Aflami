package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.SearchHistoryEntity
import com.repository.media.entity.SearchType
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchQuery(history: SearchHistoryEntity)

    @Query("SELECT * FROM search_history_table ORDER BY search_date DESC")
    fun getAllSearchQueries(): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_history_table WHERE search_query = :searchQuery AND search_type = :searchType")
    suspend fun getSearchHistoryQuery(searchQuery: String, searchType: SearchType): SearchHistoryEntity?

    @Query("DELETE FROM search_history_table WHERE search_query = :searchQuery AND search_type = :searchType")
    suspend fun clearSearchQueryByQuery(searchQuery: String, searchType: SearchType)

    @Query("DELETE FROM search_history_table")
    suspend fun clearAllSearchQueries()

}