package com.example.search

import com.example.search.api.GenresApiServices
import com.repository.search.models.GenreDto

class GenresRemoteDataSourceImp(private val apiService: GenresApiServices) :
    com.repository.search.dataSource.remote.GenresRemoteDataSource {
    override suspend fun getAllGenres(): com.repository.search.models.GenreDto {
        return apiService.getAllGenres()
    }
}