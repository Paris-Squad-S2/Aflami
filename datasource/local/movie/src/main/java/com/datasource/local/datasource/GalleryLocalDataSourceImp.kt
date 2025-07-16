package com.datasource.local.datasource

import com.datasource.local.dao.GalleryDao
import com.example.movie.dataSource.local.GalleryLocalDataSource
import com.example.movie.models.local.GalleryEntity

class GalleryLocalDataSourceImp(private val dao: GalleryDao) : GalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)
    override suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity? = dao.getGallery(movieId)
}