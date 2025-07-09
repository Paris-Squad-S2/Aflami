package com.example.search

import com.example.search.models.SearchDto

interface SearchRemoteDataSource {
    suspend fun searchMulti(query: String, page: Int = 1, language: String = "en-US"): SearchDto
    suspend fun searchPerson(query: String, page: Int = 1, language: String = "en-US"): SearchDto
    suspend fun searchCountryCode(
        query: String,
        page: Int = 1,
        language: String = "en-US",
        countryCode: String = "US",
    ): SearchDto
}