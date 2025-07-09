package com.datasource.local.search.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.search.entity.MediaEntity

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMedia(media: List<MediaEntity>)

    @Query("SELECT * FROM media_table")
    suspend fun getAllMedia(): List<MediaEntity>

    @Query("SELECT * FROM media_table WHERE country LIKE '%' || :country || '%'")
    suspend fun getMediaByCountry(country: String): List<MediaEntity>

    @Query("SELECT * FROM media_table WHERE actor LIKE '%' || :actor || '%'")
    suspend fun getMediaByActor(actor: String): List<MediaEntity>

    @Query("SELECT * FROM media_table WHERE title LIKE '%' || :query || '%'")
    suspend fun getMediaByTitleQuery(query: String): List<MediaEntity>

}