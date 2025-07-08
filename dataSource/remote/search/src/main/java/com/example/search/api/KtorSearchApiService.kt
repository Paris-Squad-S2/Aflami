package com.example.search.api

import com.example.search.models.SearchDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class KtorSearchApiService(
    private val httpClient: HttpClient,
    private val baseUrl: String,
) : SearchApiService {

    companion object {
        private const val MULTI_SEARCH_ENDPOINT = "search/multi"
        private const val PERSON_SEARCH_ENDPOINT = "search/person"
        private const val QUERY_PARAM = "query"
        private const val PAGE_PARAM = "page"
        private const val LANGUAGE_PARAM = "language"
        private const val DISCOVER_MOVIE_ENDPOINT = "discover/movie"
        private const val DISCOVER_TV_ENDPOINT = "discover/tv"
        private const val COUNTRY_PARAM = "with_origin_country"
    }

    override suspend fun searchMulti(query: String, page: Int, language: String): SearchDto {
        return performSearch(MULTI_SEARCH_ENDPOINT, query, page, language)
    }

    override suspend fun searchPerson(query: String, page: Int, language: String): SearchDto {
        return performSearch(PERSON_SEARCH_ENDPOINT, query, page, language)
    }

    override suspend fun searchCountryCode(
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): SearchDto {
        return performSearchWithCountry(
            endpoint = DISCOVER_MOVIE_ENDPOINT,
            query = query,
            page = page,
            language = language,
            countryCode = countryCode
        )
    }


    private suspend fun performSearchWithCountry(
        endpoint: String,
        query: String,
        page: Int,
        language: String,
        countryCode: String,
    ): SearchDto {
        return httpClient.get("$baseUrl/$endpoint") {
            parameter(QUERY_PARAM, query)
            parameter(PAGE_PARAM, page)
            parameter(LANGUAGE_PARAM, language)
            parameter(COUNTRY_PARAM, countryCode)
        }.body()
    }

    private suspend fun performSearch(
        endpoint: String,
        query: String,
        page: Int,
        language: String,
    ): SearchDto {
        return httpClient.get("$baseUrl/$endpoint") {
            parameter(QUERY_PARAM, query)
            parameter(PAGE_PARAM, page)
            parameter(LANGUAGE_PARAM, language)
        }.body()
    }
}
