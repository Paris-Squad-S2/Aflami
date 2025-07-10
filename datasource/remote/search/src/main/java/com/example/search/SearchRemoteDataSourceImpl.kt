package com.example.search

import com.example.search.api.SearchApiService
import com.repository.search.models.SearchDto

open class SearchRemoteDataSourceImpl(
    private val apiService: SearchApiService,
) : com.repository.search.dataSource.remote.SearchRemoteDataSource {
    override suspend fun searchMulti(query: String, page: Int, language: String): com.repository.search.models.SearchDto {
        return apiService.searchMulti(query, page, language)
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): com.repository.search.models.SearchDto {
        return apiService.searchPerson(query, page, language)
    }

    override suspend fun searchCountryCode(
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): com.repository.search.models.SearchDto {
        return apiService.searchCountryCode(query, page, language, countryCode)
    }
}