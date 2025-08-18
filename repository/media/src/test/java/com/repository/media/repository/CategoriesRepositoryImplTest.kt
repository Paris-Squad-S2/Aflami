package com.repository.media.repository

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.models.remote.media.GenreDto
import com.repository.media.models.remote.media.GenresDto
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
    private val settingLocalDataSource: SettingLocalDataSource = mockk()
    private val language = "en"

    @BeforeEach
    fun setUp() {
        repository = CategoriesRepositoryImpl(
            networkConnectionChecker,
            genresRemoteDataSource,
            settingLocalDataSource
        )
    }

    @Test
    fun `getAllCategories should fetch from remote`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        coEvery { genresRemoteDataSource.getMoviesGenres(language) } returns GenresDto(
            genreDto = listOf(GenreDto(18, "Drama"))
        )

        // When
        val result = repository.getAllCategories()

        assertEquals(listOf(Category.Drama), result)
    }

    @Test
    fun `getAllCategories should throw NoInternetConnectionException when no internet`() = runTest {
        // Given
        every { networkConnectionChecker.isConnected } returns MutableStateFlow(false)
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)

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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow(language)
        // When + Then
        val exception = assertThrows<FailedException> {
            repository.getAllCategories()
        }
        assertNotNull(exception)
    }
}

