package com.repository.media.repository

import com.paris_2.domain.media.entity.Genre
import com.paris_2.domain.media.exception.NoCategoriesFoundException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.repository.user.dataSource.local.LanguageLocalDataSourceRepository
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.dto.GenreDto
import com.repository.media.dto.GenresDto
import com.repository.media.entity.Category
import com.repository.media.util.NetworkConnectionChecker
import io.mockk.coEvery
import io.mockk.every
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
    private val networkConnectionChecker: NetworkConnectionChecker = mockk(relaxed = true)
    private val genresRemoteDataSource: GenresRemoteDataSource = mockk()
    private val languageLocalDataSourceRepository: LanguageLocalDataSourceRepository = mockk()
    private val language = "en"

    @BeforeEach
    fun setUp() {
        repository = CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresRemoteDataSource,
            languageLocalDataSourceRepository
        )
    }

    @Test
    fun `getAllCategories should fetch from remote`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { languageLocalDataSourceRepository.getLanguage() } returns MutableStateFlow(language)
        coEvery { genresRemoteDataSource.getMoviesGenres(language) } returns GenresDto(
            genreDto = listOf(GenreDto(2, "Drama"))
        )

        // When
        val result = repository.getAllCategories()

        assertEquals(listOf(Genre.Drama), result)
    }

    @Test
    fun `getAllCategories should throw NoInternetConnectionException when no internet`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(false)
        coEvery { languageLocalDataSourceRepository.getLanguage() } returns MutableStateFlow(language)

        // When + Then
        val exception = assertThrows<NoInternetConnectionException> {
            repository.getAllCategories()
        }
        assertNotNull(exception)
    }

    @Test
    fun `getAllCategories should throw NoCategoriesFoundException on exception`() = runTest {

        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { genresRemoteDataSource.getMoviesGenres(language) } throws Exception()
        coEvery { languageLocalDataSourceRepository.getLanguage() } returns MutableStateFlow(language)
        // When + Then
        val exception = assertThrows<NoCategoriesFoundException> {
            repository.getAllCategories()
        }
        assertNotNull(exception)
    }
}

