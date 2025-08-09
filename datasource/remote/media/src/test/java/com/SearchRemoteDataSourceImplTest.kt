package com

import com.google.common.truth.Truth
import com.repository.media.SearchRemoteDataSourceImpl
import com.repository.media.dto.search.SearchDto
import com.repository.media.services.MediaApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchRemoteDataSourceImplTest {

    private val mockMediaApiService = mockk<MediaApiService>()

    private val mockSearchDto = mockk<SearchDto>()
    private lateinit var searchRemoteDataSource: SearchRemoteDataSourceImpl

    @Before
    fun setUp() {
        searchRemoteDataSource = SearchRemoteDataSourceImpl(mockMediaApiService)
    }

    @Test
    fun `searchMulti should return expected SearchDto when API call is successful`() =
        runTest {
            // Given
            coEvery {
                mockMediaApiService.searchMulti(query, page, language)
            } returns mockSearchDto

            // When
            val result = searchRemoteDataSource.searchMulti(query, page, language)

            // Then
            Truth.assertThat(result).isEqualTo(mockSearchDto)
        }

    @Test
    fun `searchMulti should call searchMulti API once`() = runTest {
        // Given
        coEvery { mockMediaApiService.searchMulti(query, page, language) } returns mockSearchDto

        // When
        searchRemoteDataSource.searchMulti(query, page, language)

        // Then
        coVerify(exactly = 1) { mockMediaApiService.searchMulti(query, page, language) }
    }


    @Test
    fun `searchMulti should throw exception when API fails`() = runTest {
        //Given
        coEvery { mockMediaApiService.searchMulti(query, page, language) } throws apiException

        //When and Then
        try {
            searchRemoteDataSource.searchMulti(query, page, language)
            throw AssertionError("Should have thrown exception")
        } catch (e: Exception) {
            Truth.assertThat(e).isEqualTo(apiException)
        }
    }

    @Test
    fun `searchMulti should call searchMulti API once when exception occurs`() = runTest {
        //Given
        coEvery { mockMediaApiService.searchMulti(query, page, language) } throws apiException

        //When and Then
        try {
            searchRemoteDataSource.searchMulti(query, page, language)
        } catch (_: Exception) {
        }

        coVerify(exactly = 1) { mockMediaApiService.searchMulti(query, page, language) }
    }


    @Test
    fun `searchPerson should return expected SearchDto when API call is successful`() =
        runTest {
            // Given
            coEvery {
                mockMediaApiService.searchPerson(actorQuery, page, language)
            } returns mockSearchDto

            // When
            val result = searchRemoteDataSource.searchPerson(actorQuery, page, language)

            // Then
            Truth.assertThat(result).isEqualTo(mockSearchDto)
        }

    @Test
    fun `searchPerson should call API service exactly once`() = runTest {
        // Given
        coEvery {
            mockMediaApiService.searchPerson(
                actorQuery,
                page,
                language
            )
        } returns mockSearchDto

        // When
        searchRemoteDataSource.searchPerson(actorQuery, page, language)

        // Then
        coVerify(exactly = 1) { mockMediaApiService.searchPerson(actorQuery, page, language) }
    }

    @Test
    fun `searchPerson should propagate exception when API call fails`() =
        runTest {
            // Given
            coEvery {
                mockMediaApiService.searchPerson(actorQuery, page, language)
            } throws apiException

            // When and Then
            try {
                searchRemoteDataSource.searchPerson(actorQuery, page, language)
                throw AssertionError("Should have propagated the exception")
            } catch (e: Exception) {
                Truth.assertThat(e).isEqualTo(apiException)
            }
        }

    @Test
    fun `searchPerson should call API when exception is thrown`() = runTest {
        // Given
        coEvery {
            mockMediaApiService.searchPerson(actorQuery, page, language)
        } throws apiException

        // When
        try {
            searchRemoteDataSource.searchPerson(actorQuery, page, language)
        } catch (_: Exception) {
        }

        // Then
        coVerify(exactly = 1) {
            mockMediaApiService.searchPerson(actorQuery, page, language)
        }
    }

    @Test
    fun `searchCountryCode should return expected SearchDto when API call is successful `() =
        runTest {
            // Given
            coEvery {
                mockMediaApiService.searchCountryCode(page, language, countryCode)
            } returns mockSearchDto

            //When
            val result =
                searchRemoteDataSource.searchCountryCode(page, language, countryCode)

            // Then
            Truth.assertThat(result).isEqualTo(mockSearchDto)

        }

    @Test
    fun `searchCountryCode should call API when API call is successful`() = runTest {
        // Given
        coEvery {
            mockMediaApiService.searchCountryCode(page, language, countryCode)
        } returns mockSearchDto

        // When
        searchRemoteDataSource.searchCountryCode(page, language, countryCode)

        // Then
        coVerify(exactly = 1) {
            mockMediaApiService.searchCountryCode(page, language, countryCode)
        }
    }

    @Test
    fun `searchCountryCode should propagate exception when API call fails`() =
        runTest {
            // Given
            coEvery {
                mockMediaApiService.searchCountryCode(page, language, countryCode)
            } throws apiException

            // When and Then
            try {
                searchRemoteDataSource.searchCountryCode(page, language, countryCode)
                throw AssertionError("Should have propagated the exception")
            } catch (e: Exception) {
                Truth.assertThat(e).isEqualTo(apiException)
            }

        }

    @Test
    fun `searchCountryCode should call API when exception is thrown`() = runTest {
        // Given
        coEvery {
            mockMediaApiService.searchCountryCode(page, language, countryCode)
        } throws apiException

        // When
        try {
            searchRemoteDataSource.searchCountryCode(page, language, countryCode)
        } catch (_: Exception) {
        }

        // Then
        coVerify(exactly = 1) {
            mockMediaApiService.searchCountryCode(page, language, countryCode)
        }
    }


    private companion object {
        val actorQuery = "chris evans"
        val query = "avengers"
        val page = 1
        val language = "en-US"
        val countryCode = "US"
        val apiException = RuntimeException("API Error")
    }
}