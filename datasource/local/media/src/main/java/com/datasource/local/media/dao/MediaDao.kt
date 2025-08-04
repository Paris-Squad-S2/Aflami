package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.media.entity.MediaSearchEntity
import com.repository.media.entity.SearchType


@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMedia(media: List<MediaSearchEntity>)

    @Query("SELECT * FROM media_table")
    suspend fun getAllMedia(): List<MediaSearchEntity>

    @Query("SELECT * FROM media_table WHERE searchQuery = :country AND searchType = 'Country' And page = :page And language = :language")
    suspend fun getMediaByCountry(country: String,page: Int,language: String): List<MediaSearchEntity>

    @Query("SELECT * FROM media_table WHERE searchQuery = :actor AND searchType = 'Actor' And page = :page And language = :language")
    suspend fun getMediaByActor(actor: String,page:Int,language: String): List<MediaSearchEntity>

    @Query("SELECT * FROM media_table WHERE searchQuery = :query AND searchType = 'Query' And page = :page And language = :language")
    suspend fun getMediaByTitleQuery(query: String,page: Int,language: String): List<MediaSearchEntity>

    @Query("SELECT media_table.* FROM media_table INNER JOIN search_history_table ON media_table.searchQuery = search_history_table.search_query")
    suspend fun getCachedMedia(): List<MediaSearchEntity>

    @Query("DELETE FROM media_table WHERE searchQuery = :searchQuery AND searchType = :searchType")
    suspend fun clearAllMediaBySearchQuery(searchQuery: String, searchType: SearchType)

}