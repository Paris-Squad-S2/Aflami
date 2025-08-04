package com.repository.media

import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.dto.search.SearchDto
import com.repository.media.services.RetrofitSearchApiService
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