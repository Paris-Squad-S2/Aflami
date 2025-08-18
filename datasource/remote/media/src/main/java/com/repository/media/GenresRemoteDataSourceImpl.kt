package com.repository.media

import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.models.remote.media.GenresDto
import com.repository.media.services.GenresApiServices
import javax.inject.Inject

class GenresRemoteDataSourceImpl @Inject constructor(
    private val apiService: GenresApiServices
): GenresRemoteDataSource {
    override suspend fun getMoviesGenres(language: String): GenresDto {
        return apiService.getMoviesGenres(language)
    }
}