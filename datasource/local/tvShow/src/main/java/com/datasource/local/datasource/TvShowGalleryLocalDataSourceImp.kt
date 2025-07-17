package com.datasource.local.datasource

import com.datasource.local.dao.TvShowGalleryDao
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.model.local.GalleryEntity

class TvShowGalleryLocalDataSourceImp(private val dao: TvShowGalleryDao) : TvShowGalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)

    override suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity? =
        dao.getGalleryByTvShowId(tvShowId)
}