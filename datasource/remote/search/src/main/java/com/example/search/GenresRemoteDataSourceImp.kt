package com.example.search

import com.example.search.service.contract.GenresApiServices
import com.example.search.models.GenresDto

class GenresRemoteDataSourceImp(private val apiService: GenresApiServices) : GenresRemoteDataSource {
    override suspend fun getAllGenres(): GenresDto {
        return apiService.getAllGenres()
    }
}
