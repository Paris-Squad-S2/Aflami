package com.example.search.service.contract

import com.repository.search.dto.SearchDto


interface SearchApiService {
    suspend fun searchMulti(query: String, page: Int, language: String): SearchDto
    suspend fun searchPerson(query: String, page: Int, language: String): SearchDto
    suspend fun searchCountryCode(query: String, page: Int, language: String, countryCode: String, ): SearchDto
}