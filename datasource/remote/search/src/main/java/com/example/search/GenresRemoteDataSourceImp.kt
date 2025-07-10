package com.example.search

import com.example.search.service.contract.GenresApiServices
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.GenresDto

class GenresRemoteDataSourceImp(private val apiService: GenresApiServices) :
    GenresRemoteDataSource {
    override suspend fun getAllGenres(): GenresDto {
        return apiService.getAllGenres()
    }
}
