package com.repository.home

import com.repository.home.datasource.remote.GenresRemoteDataSource
import com.repository.home.dto.GenresDto
import javax.inject.Inject

class GenresDataSourceImpl @Inject constructor(
    private val apiService: GenresApiServices
): GenresRemoteDataSource {
    override suspend fun getMoviesGenres(language: String): GenresDto {
        return apiService.getMoviesGenres(language)
    }
}