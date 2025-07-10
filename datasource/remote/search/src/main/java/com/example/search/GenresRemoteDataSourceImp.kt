package com.example.search

import com.example.search.api.GenresApiServices
import com.example.search.models.GenreDto

class GenresRemoteDataSourceImp(private val apiService: GenresApiServices) :
    GenresRemoteDataSource {
    override suspend fun getAllGenres(): GenreDto {
        return apiService.getAllGenres()
    }
}