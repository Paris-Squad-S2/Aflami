package com.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.movie.models.local.GalleryEntity

@Dao
interface MovieGalleryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGallery(gallery: GalleryEntity)

    @Query("SELECT * FROM gallery_table WHERE movieId = :movieId")
    suspend fun getGallery(movieId: Int): GalleryEntity?
}