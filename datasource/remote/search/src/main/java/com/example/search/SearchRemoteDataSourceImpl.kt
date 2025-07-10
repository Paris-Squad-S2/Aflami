package com.example.search

import com.example.search.models.SearchDto
import com.example.search.exception.safeApiCall
import com.example.search.service.contract.SearchApiService

class SearchRemoteDataSourceImpl(private val apiService: SearchApiService) : SearchRemoteDataSource {

    override suspend fun searchMulti(query: String, page: Int, language: String): SearchDto {
        return safeApiCall("Failed to perform multi search") {
            apiService.searchMulti(query, page, language)
        }
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): SearchDto {
        return safeApiCall("Failed to search for person") {
            apiService.searchPerson(query, page, language)
        }
    }

    override suspend fun searchCountryCode(
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): SearchDto {
        return safeApiCall("Failed to search by country") {
            apiService.searchCountryCode(query, page, language, countryCode)
        }
    }
}
