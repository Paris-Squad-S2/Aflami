package com.repository.movie.dataSource.local

import com.repository.movie.models.local.GalleryEntity

interface GalleryLocalDataSource {
    suspend fun addGallery(gallery: GalleryEntity)
    suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity?
}