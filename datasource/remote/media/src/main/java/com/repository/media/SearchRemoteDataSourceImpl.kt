package com.repository.media

import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.dto.search.SearchDto
import com.repository.media.services.MediaApiService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(private val mediaApiService: MediaApiService) :
    SearchRemoteDataSource {

    override suspend fun searchMulti(query: String, page: Int, language: String): SearchDto {
        return mediaApiService.searchMulti(query, page, language)
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): SearchDto {
        return mediaApiService.searchPerson(query, page, language)
    }

    override suspend fun searchCountryCode(page: Int, language: String, countryCode: String): SearchDto {
        return mediaApiService.searchCountryCode( page, language, countryCode)
    }
}