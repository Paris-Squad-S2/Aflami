package com.repository.search

import com.repository.search.service.contract.SearchApiService
import com.google.common.truth.Truth.assertThat
import com.repository.search.dto.SearchDto
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
    fun `searchMulti should return expected SearchDto when API call is successful`() =
        runTest {
            // Given
            val query = "avengers"
            val page = 1
            val language = "en-US"
            val mockSearchDto = mockk<SearchDto>()
            // When
            coEvery {
                mockSearchApiService.searchMulti(query, page, language)
            } returns mockSearchDto
            val result = searchRemoteDataSource.searchMulti(query, page, language)
            // Then
            assertThat(result).isEqualTo(mockSearchDto)
            coVerify(exactly = 1) { mockSearchApiService.searchMulti(query, page, language) }
        }

    @Test
    fun `searchMulti should propagate exception when API call fails`() =
        runTest {
            // Given
            val query = "avengers"
            val page = 1
            val language = "en-US"
            val apiException = RuntimeException("API Error")
            // When
            coEvery {
                mockSearchApiService.searchMulti(query, page, language)
            } throws apiException

            // Then
            try {
                searchRemoteDataSource.searchMulti(query, page, language)
                throw AssertionError("Should have propagated the exception")
            } catch (e: Exception) {
                assertThat(e).isEqualTo(apiException)
            }
            coVerify(exactly = 1) { mockSearchApiService.searchMulti(query, page, language) }
        }

    @Test
    fun `searchPerson should return expected SearchDto when API call is successful`() =
        runTest {
            // Given
            val query = "chris evans"
            val page = 1
            val language = "en-US"
            val mockSearchDto = mockk<SearchDto>()

            // When
            coEvery {
                mockSearchApiService.searchPerson(query, page, language)
            } returns mockSearchDto
            val result = searchRemoteDataSource.searchPerson(query, page, language)

            // Then
            assertThat(result).isEqualTo(mockSearchDto)
            coVerify(exactly = 1) { mockSearchApiService.searchPerson(query, page, language) }
        }

    @Test
    fun `searchPerson should propagate exception when API call fails`() =
        runTest {
            // Given
            val query = "chris evans"
            val page = 1
            val language = "en-US"
            val apiException = RuntimeException("API Error")
            // When
            coEvery {
                mockSearchApiService.searchPerson(query, page, language)
            } throws apiException

            // Then
            try {
                searchRemoteDataSource.searchPerson(query, page, language)
                throw AssertionError("Should have propagated the exception")
            } catch (e: Exception) {
                assertThat(e).isEqualTo(apiException)
            }
            coVerify(exactly = 1) { mockSearchApiService.searchPerson(query, page, language) }
        }

    @Test
    fun `searchCountryCode should return expected SearchDto when API call is successful `() =
        runTest {
            // Given
            val query = "avengers"
            val page = 1
            val language = "en-US"
            val countryCode = "US"
            val mockSearchDto = mockk<SearchDto>()
            // When
            coEvery {
                mockSearchApiService.searchCountryCode(query, page, language, countryCode)
            } returns mockSearchDto

            val result =
                searchRemoteDataSource.searchCountryCode(query, page, language, countryCode)

            // Then
            assertThat(result).isEqualTo(mockSearchDto)
            coVerify(exactly = 1) {
                mockSearchApiService.searchCountryCode(query, page, language, countryCode)
            }
        }

    @Test
    fun `searchCountryCode should propagate exception when API call fails`() =
        runTest {
            // Given
            val query = "avengers"
            val page = 1
            val language = "en-US"
            val countryCode = "US"
            val apiException = RuntimeException("API Error")
            // When
            coEvery {
                mockSearchApiService.searchCountryCode(query, page, language, countryCode)
            } throws apiException

            // Then

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
