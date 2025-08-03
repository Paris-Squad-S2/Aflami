package com.repository.search.repository

import com.paris_2.domain.media.entity.Category
import com.repository.search.util.NetworkConnectionChecker
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.GenreDto
import com.repository.search.dto.GenresDto
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoriesRepositoryImplTest {

    private lateinit var repository: CategoriesRepositoryImpl
    private val networkConnectionChecker = mockk<NetworkConnectionChecker>(relaxed = true)
    private val genresRemoteDataSource = mockk<GenresRemoteDataSource>()
    private val language = "en"

    @BeforeEach
    fun setUp() {
        repository = CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresRemoteDataSource
        )
    }


    @Test
    fun `getAllCategories should fetch from remote when local is empty and internet available`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { genresRemoteDataSource.getAllGenres(language) } returns GenresDto(
            genreDto = listOf(GenreDto(2, "Drama"))
        )

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(listOf(Category(2, "Drama")), result)
        coVerify { genresRemoteDataSource.getAllGenres(language) }
    }

    @Test
    fun `getAllCategories should throw NoInternetConnectionException when local empty and no internet`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        // When + Then
        val exception = assertThrows<NoInternetConnectionException> {
            repository.getAllCategories()
        }

        assertNotNull(exception)
    }

    @Test
    fun `getAllCategories should throw NoCategoriesFoundException on exception`() = runTest {

        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { genresRemoteDataSource.getAllGenres(language) } throws Exception()
        // When + Then
        val exception = assertThrows<NoCategoriesFoundException> {
            repository.getAllCategories()
        }
        assertNotNull(exception)
    }
}
