package com.repository.search.dataSource.remote

import com.repository.search.dto.SearchDto

interface SearchRemoteDataSource {
    suspend fun searchMulti(query: String, page: Int = 1, language: String = "en-US"): SearchDto
    suspend fun searchPerson(query: String, page: Int = 1, language: String = "en-US"): SearchDto
    suspend fun searchCountryCode(
        query: String,
        page: Int = 1,
        language: String = "en-US",
        countryCode: String,
    ): SearchDto
}