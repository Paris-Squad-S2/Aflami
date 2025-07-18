package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.GalleryEntity

@Dao
interface TvShowGalleryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGallery(gallery: GalleryEntity)

    @Query("SELECT * FROM gallery_table WHERE tvShowId = :tvShowId")
    suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity?
}