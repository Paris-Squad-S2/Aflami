package com.repository.datasorce.local

import com.repository.entity.ImageEntity

interface ImageLocalDataSource {
    suspend fun addImage(images: ImageEntity)
    suspend fun getImage(): ImageEntity
}