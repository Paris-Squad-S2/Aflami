package com.repository.home.repository

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.repository.media.datasource.local.GenresLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.dto.GenreDto
import com.repository.media.dto.GenresDto
import com.repository.media.entity.GenreEntity
import com.repository.media.repository.CategoriesRepositoryImpl
import com.repository.media.util.NetworkConnectionChecker
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
    private val genresLocalDataSource = mockk<GenresLocalDataSource>()
    private val genresRemoteDataSource = mockk<GenresRemoteDataSource>()
    private val language = "en"

    @BeforeEach
    fun setUp() {
        repository = CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresLocalDataSource,
            genresRemoteDataSource
        )
    }

    @Test
    fun `getAllCategories should return local categories if not empty`() = runTest {
        // Given
        val localGenres = listOf(GenreEntity(1, "Action", language))
        coEvery { genresLocalDataSource.getGenres(language) } returns localGenres

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(localGenres.map { Category(it.id, it.name) }, result)
        coVerify(exactly = 0) { genresRemoteDataSource.getMoviesGenres(language) }
    }

    @Test
    fun `getAllCategories should fetch from remote when local is empty and internet available`() = runTest {
        // Given
        coEvery { genresLocalDataSource.getGenres(language) } returnsMany listOf(emptyList(), listOf(GenreEntity(2, "Drama" , language)))
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { genresRemoteDataSource.getMoviesGenres(language) } returns GenresDto(
            genreDto = listOf(GenreDto(2, "Drama"))
        )
        coEvery { genresLocalDataSource.addGenres(any()) } just Runs

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(listOf(Category(2, "Drama")), result)
        coVerify { genresRemoteDataSource.getMoviesGenres(language) }
    }

    @Test
    fun `getAllCategories should throw NoInternetConnectionException when local empty and no internet`() = runTest {
        // Given
        coEvery { genresLocalDataSource.getGenres(language) } returns emptyList()
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        // When + Then
        val exception = assertThrows<NoInternetConnectionException> {
            repository.getAllCategories()
        }

        assertNotNull(exception)
    }

    @Test
    fun `getAllCategories should throw NoCategoriesFoundException on exception`() = runTest {
        // Given
        coEvery { genresLocalDataSource.getGenres(language) } throws RuntimeException("DB error")

        // When + Then
        val exception = assertThrows<NoCategoriesFoundException> {
            repository.getAllCategories()
        }

        assertNotNull(exception)
    }
}
