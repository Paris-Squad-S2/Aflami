package com.repository.search

import com.repository.search.service.contract.GenresApiServices
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.GenresDto

class GenresRemoteDataSourceImp(private val apiService: GenresApiServices) :
    GenresRemoteDataSource {
    override suspend fun getAllGenres(language: String): GenresDto {
        return apiService.getAllGenres(language)
    }
}
