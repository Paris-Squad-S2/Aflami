package com.paris_2.home

import com.paris_2.home.datasource.remote.GenresRemoteDataSource
import com.paris_2.home.dto.GenresDto

class GenresDataSourceImpl(
    private val apiService: GenresApiServices
): GenresRemoteDataSource {
    override suspend fun getAllGenres(language: String): GenresDto {
        return apiService.getAllGenres(language)
    }
}