package com.datasource.local.datasource

import com.datasource.local.dao.ImageDao
import com.repository.datasource.local.ImageLocalDataSource
import com.repository.entity.ImageEntity

class ImageLocalDataSourceImp(private val imageDao: ImageDao) : ImageLocalDataSource {
    override suspend fun addImage(images: ImageEntity) = imageDao.addImage(images)

    override suspend fun getImage(): ImageEntity = imageDao.getImage()
}