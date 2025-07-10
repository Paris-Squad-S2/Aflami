package com.example.search.service.contract

import com.repository.search.models.SearchDto

interface SearchApiService {
    suspend fun searchMulti(query: String, page: Int, language: String): com.repository.search.models.SearchDto
    suspend fun searchPerson(query: String, page: Int, language: String): com.repository.search.models.SearchDto
    suspend fun searchCountryCode(
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): com.repository.search.models.SearchDto
}