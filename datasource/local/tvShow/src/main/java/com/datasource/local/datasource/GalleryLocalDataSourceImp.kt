package com.datasource.local.datasource

import com.datasource.local.dao.GalleryDao
import com.repository.dataSource.local.GalleryLocalDataSource
import com.repository.model.local.GalleryEntity

class GalleryLocalDataSourceImp(private val dao: GalleryDao) : GalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)

    override suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity? =
        dao.getGalleryByTvShowId(tvShowId)
}