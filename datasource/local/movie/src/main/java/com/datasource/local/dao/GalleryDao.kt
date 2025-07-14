package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.entity.GalleryEntity

@Dao
interface GalleryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGallery(gallery: List<GalleryEntity>)
    @Query("SELECT * FROM gallery_table")
    suspend fun getGallery(): List<GalleryEntity>
}