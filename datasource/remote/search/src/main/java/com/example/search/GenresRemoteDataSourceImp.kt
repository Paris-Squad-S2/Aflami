package com.example.search

import com.example.search.service.contract.GenresApiServices
import com.example.search.models.GenresDto
import com.example.search.exception.safeApiCall

class GenresRemoteDataSourceImp(private val apiService: GenresApiServices) : GenresRemoteDataSource {
    override suspend fun getAllGenres(): GenresDto {
        return safeApiCall("Error fetching all genres") {
            apiService.getAllGenres()
        }
    }
}
