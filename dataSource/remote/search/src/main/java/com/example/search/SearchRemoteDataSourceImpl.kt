package com.example.search

import com.example.search.api.SearchApiService
import com.example.search.models.SearchDto

open class SearchRemoteDataSourceImpl(
    private val apiService: SearchApiService
) : SearchRemoteDataSource {
    override suspend fun searchMulti(query: String, page: Int, language: String): SearchDto {
        return apiService.searchMulti(query, page, language)
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): SearchDto {
        return apiService.searchPerson(query, page, language)
    }
}
