package com.repository.datasource.local

import com.repository.entity.GalleryEntity

interface GalleryLocalDataSource {
    suspend fun addGallery(image: List<GalleryEntity>)
    suspend fun getGallery(): List<GalleryEntity>
}