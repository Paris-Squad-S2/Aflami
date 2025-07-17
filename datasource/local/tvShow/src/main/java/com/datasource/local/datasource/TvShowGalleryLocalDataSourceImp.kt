package com.datasource.local.datasource

import com.datasource.local.dao.TvShowCastGalleryDao
import com.repository.dataSource.local.GalleryLocalDataSource
import com.repository.model.local.GalleryEntity

class TvShowGalleryLocalDataSourceImp(private val dao: TvShowCastGalleryDao) : GalleryLocalDataSource {
    override suspend fun addGallery(gallery: GalleryEntity) = dao.addGallery(gallery)

    override suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity? =
        dao.getGalleryByTvShowId(tvShowId)
}