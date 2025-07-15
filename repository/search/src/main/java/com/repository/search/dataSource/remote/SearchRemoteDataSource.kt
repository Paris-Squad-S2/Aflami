package com.repository.search.dataSource.remote

import com.repository.search.dto.SearchDto

interface SearchRemoteDataSource {
    suspend fun searchMulti(query: String, page: Int = 1, language: String ): SearchDto
    suspend fun searchPerson(query: String, page: Int = 1, language: String ): SearchDto
    suspend fun searchCountryCode(query: String, page: Int = 1, language: String, countryCode: String): SearchDto
}