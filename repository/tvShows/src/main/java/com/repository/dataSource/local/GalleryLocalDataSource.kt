package com.repository.dataSource.local

import com.repository.model.local.GalleryEntity

interface GalleryLocalDataSource {
    suspend fun addGallery(gallery: GalleryEntity)
    suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity?
}