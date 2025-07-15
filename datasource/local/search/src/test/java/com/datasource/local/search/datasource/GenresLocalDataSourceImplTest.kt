package com.datasource.local.search.datasource

import com.datasource.local.search.dao.GenresDao
import com.google.common.truth.Truth.assertThat
import com.repository.search.entity.GenreEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GenresLocalDataSourceImplTest {
    private lateinit var genresLocalDataSource: GenresLocalDataSourceImpl
    private val genresDao: GenresDao = mockk(relaxed = false)
    private lateinit var sampleGenre: GenreEntity

    @BeforeEach
    fun setUp() {
        genresLocalDataSource = GenresLocalDataSourceImpl(genresDao)
        sampleGenre = GenreEntity(
            id = 1,
            name = "all"
        )
    }

    @Test
    fun `getGenres should return genres when getAll in GenresDao called successfully`() =
        runTest {
            // Given
            coEvery { genresDao.getGenres() } returns listOf(sampleGenre)
            // When
            val result = genresLocalDataSource.getGenres()
            // Then
            assertThat(result).containsExactly(sampleGenre)
        }

    @Test
    fun `getGenres should return empty list when GenresDao returns nothing`() =
        runTest {
            // Given
            coEvery { genresDao.getGenres() } returns emptyList()
            // When
            val result = genresLocalDataSource.getGenres()
            // Then
            assertThat(result).isEmpty()
        }

    @Test
    fun `addGenres should add Genres when add in GenresDao called successfully`() =
        runTest {
            // Given
            coEvery { genresDao.addGenres(any()) } returns Unit
            // When
            genresLocalDataSource.addGenres(listOf(sampleGenre))
            // Then
            coVerify {
                genresDao.addGenres(withArg {
                    assertThat(it).isEqualTo(listOf(sampleGenre))
                })
            }
        }
}