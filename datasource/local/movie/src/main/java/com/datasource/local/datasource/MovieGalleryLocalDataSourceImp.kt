package com.datasource.local.datasource

import com.datasource.local.dao.MovieGalleryDao
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.models.local.GalleryEntity

class MovieGalleryLocalDataSourceImp(private val dao: MovieGalleryDao) : MovieGalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)
    override suspend fun getGalleryByMovieId(movieId: Int): GalleryEntity? = dao.getGallery(movieId)
}