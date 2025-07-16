package com.example.movie.dataSource.local

import com.example.movie.models.local.GalleryEntity

interface GalleryLocalDataSource {
    suspend fun addGallery(gallery: GalleryEntity)
    suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity?
}