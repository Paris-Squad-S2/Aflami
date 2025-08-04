package com.repository.media.datasource.remote

import com.repository.media.dto.search.SearchDto

interface SearchRemoteDataSource {
    suspend fun searchMulti(query: String, page: Int , language: String ): SearchDto
    suspend fun searchPerson(query: String, page: Int , language: String ): SearchDto
    suspend fun searchCountryCode( page: Int , language: String, countryCode: String): SearchDto
}