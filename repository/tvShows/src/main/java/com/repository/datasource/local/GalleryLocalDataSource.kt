package com.repository.datasource.local

import com.repository.entity.GalleryEntity

interface GalleryLocalDataSource {
    suspend fun addGallery(gallery: GalleryEntity)
    suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity?
}