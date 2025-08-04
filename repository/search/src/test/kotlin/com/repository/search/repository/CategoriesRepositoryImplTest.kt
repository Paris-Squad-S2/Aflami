package com.repository.search.repository

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.GenreDto
import com.repository.search.dto.GenresDto
import com.repository.search.entity.GenreEntity
import com.repository.search.util.SearchNetworkConnectionChecker
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoriesRepositoryImplTest {

    private lateinit var repository: CategoriesRepositoryImpl
    private val networkConnectionChecker = mockk<SearchNetworkConnectionChecker>(relaxed = true)
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
        val localGenres = listOf(GenreEntity(1, "Action" , language))
        coEvery { genresLocalDataSource.getGenres(language) } returns localGenres

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(localGenres.map { Category(it.id, it.name) }, result)
        coVerify(exactly = 0) { genresRemoteDataSource.getAllGenres(language) }
    }

    @Test
    fun `getAllCategories should fetch from remote when local is empty and internet available`() = runTest {
        // Given
        coEvery { genresLocalDataSource.getGenres(language) } returnsMany listOf(emptyList(), listOf(GenreEntity(2, "Drama" , language)))
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { genresRemoteDataSource.getAllGenres(language) } returns GenresDto(
            genreDto = listOf(GenreDto(2, "Drama"))
        )
        coEvery { genresLocalDataSource.addGenres(any()) } just Runs

        // When
        val result = repository.getAllCategories()

        // Then
        assertEquals(listOf(Category(2, "Drama")), result)
        coVerify { genresRemoteDataSource.getAllGenres(language) }
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
