package com.datasource.local.datasorce

import com.datasource.local.dao.GalleryDao
import com.repository.datasorce.local.GalleryLocalDataSource
import com.repository.entity.GalleryEntity

class GalleryLocalDataSourceImp(private val dao: GalleryDao) : GalleryLocalDataSource {
    override suspend fun addGallery(image: List<GalleryEntity>) = dao.addGallery(image)
    override suspend fun getGallery(): List<GalleryEntity> = dao.getGallery()
}