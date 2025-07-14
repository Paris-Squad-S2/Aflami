package com.datasource.local.datasorce

import com.datasource.local.dao.ImageDao
import com.repository.datasorce.local.ImageLocalDataSource
import com.repository.entity.ImageEntity

class ImageLocalDataSourceImp(private val imageDao: ImageDao) : ImageLocalDataSource {
    override suspend fun addImage(images: ImageEntity) = imageDao.addImage(images)

    override suspend fun getImage(): ImageEntity = imageDao.getImage()
}