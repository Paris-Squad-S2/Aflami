package com.datasource.local.datasource

import com.datasource.local.dao.GalleryDao
import com.repository.movie.dataSource.local.GalleryLocalDataSource
import com.repository.movie.models.local.GalleryEntity

class MovieGalleryLocalDataSourceImp(private val dao: GalleryDao) : GalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)
    override suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity? = dao.getGallery(movieId)
}