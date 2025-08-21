package com.repository.media.repository

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.exception.FailedException
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.models.remote.media.GenreDto
import com.repository.media.models.remote.media.GenresDto
import com.repository.media.mapper.genreListToCategoryList
import com.repository.media.util.NetworkConnectionChecker
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoviesCategoriesRepositoryImplTest {

    private val genresRemoteDataSource: GenresRemoteDataSource = mockk()
    private val networkChecker: NetworkConnectionChecker = mockk()
    private val settingLocalDataSource :SettingLocalDataSource = mockk()
    private lateinit var repo: MoviesCategoriesRepositoryImpl

    @BeforeEach
    fun setUp() {
        every { networkChecker.isConnected } returns MutableStateFlow(true)
        repo = MoviesCategoriesRepositoryImpl(
            networkConnectionChecker = networkChecker,
            genresRemoteDataSource = genresRemoteDataSource,
            settingLocalDataSource = settingLocalDataSource
        )
    }

    @Test
    fun `getMoviesCategories returns categories from data source`() = runTest {
        val genresDto = GenresDto(
            genreDto = listOf(
                GenreDto(id = 28, name = "Action"),
                GenreDto(id = 18, name = "Drama")
            )
        )
        val categories = listOf(Category.Action, Category.Drama)
        coEvery { genresRemoteDataSource.getMoviesGenres("en-US") } returns genresDto
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")

        assertThat(genresDto.genreListToCategoryList()).isEqualTo(categories)
    }

    @Test
    fun `getMoviesCategories throws NoCategoriesFoundException when categories empty`() = runTest {
        val genresDto = mockk<GenresDto>(relaxed = true)
        coEvery { genresRemoteDataSource.getMoviesGenres(any()) } returns genresDto
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        every { genresDto.genreListToCategoryList() } returns emptyList()

        assertThrows<FailedException> {
            repo.getMoviesCategories()
        }
    }
}


