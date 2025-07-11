package com.repository.search.repository

import com.domain.search.model.CategoryModel
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.GenreDto
import com.repository.search.dto.GenresDto
import com.repository.search.entity.GenreEntity
import com.repository.search.exception.NoCategoriesFoundException
import com.repository.search.exception.NoInternetConnectionException
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
        val localGenres = listOf(GenreEntity(1, "Action"))
        coEvery { genresLocalDataSource.getGenres() } returns localGenres

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(localGenres.map { CategoryModel(it.id.toInt(), it.name) }, result)
        coVerify(exactly = 0) { genresRemoteDataSource.getAllGenres() }
    }

    @Test
    fun `getAllCategories should fetch from remote when local is empty and internet available`() = runTest {
        // Given
        coEvery { genresLocalDataSource.getGenres() } returnsMany listOf(emptyList(), listOf(GenreEntity(2, "Drama")))
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { genresRemoteDataSource.getAllGenres() } returns GenresDto(
            genreDto = listOf(GenreDto(2, "Drama"))
        )
        coEvery { genresLocalDataSource.addGenres(any()) } just Runs

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(listOf(CategoryModel(2, "Drama")), result)
        coVerify { genresRemoteDataSource.getAllGenres() }
    }

    @Test
    fun `getAllCategories should throw NoInternetConnectionException when local empty and no internet`() = runTest {
        // Given
        coEvery { genresLocalDataSource.getGenres() } returns emptyList()
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
        coEvery { genresLocalDataSource.getGenres() } throws RuntimeException("DB error")

        // When + Then
        val exception = assertThrows<NoCategoriesFoundException> {
            repository.getAllCategories()
        }

        assertNotNull(exception)
    }
}
