package com.repository.home

import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.dto.GenresDto
import javax.inject.Inject

class GenresDataSourceImpl @Inject constructor(
    private val apiService: GenresApiServices
): GenresRemoteDataSource {
    override suspend fun getMoviesGenres(language: String): GenresDto {
        return apiService.getMoviesGenres(language)
    }
}