package com.repository.search

import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.dto.SearchDto
import com.repository.search.service.implementation.RetrofitSearchApiService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(private val retrofitSearchApiService: RetrofitSearchApiService) :
    SearchRemoteDataSource {

    override suspend fun searchMulti(query: String, page: Int, language: String): SearchDto {
        return retrofitSearchApiService.searchMulti(query, page, language)
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): SearchDto {
        return retrofitSearchApiService.searchPerson(query, page, language)
    }

    override suspend fun searchCountryCode(page: Int, language: String, countryCode: String): SearchDto {
        return retrofitSearchApiService.searchCountryCode( page, language, countryCode)
    }
}
