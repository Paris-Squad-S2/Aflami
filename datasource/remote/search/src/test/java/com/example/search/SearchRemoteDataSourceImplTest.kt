package com.example.search

import com.repository.search.dto.SearchDto
import com.example.search.service.contract.SearchApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchRemoteDataSourceImplTest {

    private val mockSearchApiService = mockk<SearchApiService>()
    private lateinit var searchRemoteDataSource: SearchRemoteDataSourceImpl

    @Before
    fun setUp() {
        searchRemoteDataSource = SearchRemoteDataSourceImpl(mockSearchApiService)
    }

    @Test
    fun `when searchMulti is called with successful response then return expected SearchDto`() = runTest {
        val query = "avengers"
        val page = 1
        val language = "en-US"
        val mockSearchDto = mockk<SearchDto>()
        coEvery {
            mockSearchApiService.searchMulti(query, page, language)
        } returns mockSearchDto

        val result = searchRemoteDataSource.searchMulti(query, page, language)

        assertThat(result).isEqualTo(mockSearchDto)
        coVerify(exactly = 1) { mockSearchApiService.searchMulti(query, page, language) }
    }

    @Test
    fun `when searchMulti is called with exception then propagate exception`() = runTest {
        val query = "avengers"
        val page = 1
        val language = "en-US"
        val apiException = RuntimeException("API Error")
        coEvery {
            mockSearchApiService.searchMulti(query, page, language)
        } throws apiException

        try {
            searchRemoteDataSource.searchMulti(query, page, language)
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }

        coVerify(exactly = 1) { mockSearchApiService.searchMulti(query, page, language) }
    }

    @Test
    fun `when searchPerson is called with successful response then return expected SearchDto`() = runTest {
        val query = "chris evans"
        val page = 1
        val language = "en-US"
        val mockSearchDto = mockk<SearchDto>()
        coEvery {
            mockSearchApiService.searchPerson(query, page, language)
        } returns mockSearchDto

        val result = searchRemoteDataSource.searchPerson(query, page, language)

        assertThat(result).isEqualTo(mockSearchDto)
        coVerify(exactly = 1) { mockSearchApiService.searchPerson(query, page, language) }
    }

    @Test
    fun `when searchPerson is called with exception then propagate exception`() = runTest {
        val query = "chris evans"
        val page = 1
        val language = "en-US"
        val apiException = RuntimeException("API Error")
        coEvery {
            mockSearchApiService.searchPerson(query, page, language)
        } throws apiException

        try {
            searchRemoteDataSource.searchPerson(query, page, language)
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }

        coVerify(exactly = 1) { mockSearchApiService.searchPerson(query, page, language) }
    }

    @Test
    fun `when searchCountryCode is called with successful response then return expected SearchDto`() = runTest {
        val query = "avengers"
        val page = 1
        val language = "en-US"
        val countryCode = "US"
        val mockSearchDto = mockk<SearchDto>()
        coEvery {
            mockSearchApiService.searchCountryCode(query, page, language, countryCode)
        } returns mockSearchDto

        val result = searchRemoteDataSource.searchCountryCode(query, page, language, countryCode)

        assertThat(result).isEqualTo(mockSearchDto)
        coVerify(exactly = 1) {
            mockSearchApiService.searchCountryCode(query, page, language, countryCode)
        }
    }

    @Test
    fun `when searchCountryCode is called with exception then propagate exception`() = runTest {
        val query = "avengers"
        val page = 1
        val language = "en-US"
        val countryCode = "US"
        val apiException = RuntimeException("API Error")
        coEvery {
            mockSearchApiService.searchCountryCode(query, page, language, countryCode)
        } throws apiException

        try {
            searchRemoteDataSource.searchCountryCode(query, page, language, countryCode)
            throw AssertionError("Should have propagated the exception")
        } catch (e: Exception) {
            assertThat(e).isEqualTo(apiException)
        }

        coVerify(exactly = 1) {
            mockSearchApiService.searchCountryCode(query, page, language, countryCode)
        }
    }
}
