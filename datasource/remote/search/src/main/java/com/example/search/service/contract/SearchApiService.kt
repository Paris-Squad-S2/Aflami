package com.example.search.service.contract

import com.example.search.models.SearchDto

interface SearchApiService {
    suspend fun searchMulti(query: String, page: Int, language: String): SearchDto
    suspend fun searchPerson(query: String, page: Int, language: String): SearchDto
    suspend fun searchCountryCode(
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): SearchDto
}