package com.repository.home

import com.repository.home.datasource.remote.GenresRemoteDataSource
import com.repository.home.dto.GenresDto

class GenresDataSourceImpl(
    private val apiService: GenresApiServices
): GenresRemoteDataSource {
    override suspend fun getAllGenres(language: String): GenresDto {
        return apiService.getAllGenres(language)
    }
}