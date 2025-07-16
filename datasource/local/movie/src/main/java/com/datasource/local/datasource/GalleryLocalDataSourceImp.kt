package com.datasource.local.datasource

import com.datasource.local.dao.GalleryDao
import com.repository.datasource.local.GalleryLocalDataSource
import com.repository.entity.GalleryEntity

class GalleryLocalDataSourceImp(private val dao: GalleryDao) : GalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)
    override suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity? = dao.getGallery(movieId)
}