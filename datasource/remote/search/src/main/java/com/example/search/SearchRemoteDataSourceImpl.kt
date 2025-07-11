package com.example.search

import android.util.Log
import com.example.search.service.contract.SearchApiService
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.dto.SearchDto

class SearchRemoteDataSourceImpl(private val apiService: SearchApiService) :
    SearchRemoteDataSource {

    override suspend fun searchMulti(query: String, page: Int, language: String): SearchDto {
        return apiService.searchMulti(query, page, language)
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): SearchDto {
        return apiService.searchPerson(query, page, language)
    }

    override suspend fun searchCountryCode(
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): SearchDto {
        val api = apiService.searchCountryCode(query, page, language, countryCode)
        Log.d("TAG", "searchCountryCode:DataSourceImp: ${api.results?.size}")
        api.results?.forEach {
            Log.d("TAG", "searchCountryCode:DataSourceImp: ${it}")
        }
        return api
    }
}
