package com.datasource.local.search.datasource

import com.datasource.local.search.dao.GenresDao
import com.repository.search.entity.GenreEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
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
            id = "1",
            name = "all"
        )
    }

    @Test
    fun `getGenres should return genres when getAll in GenresDao called successfully`() =
        runTest {
            coEvery { genresDao.getGenres() } returns listOf(sampleGenre)

            val result = genresLocalDataSource.getGenres()

            assertEquals(sampleGenre.name, result[0].name)
        }

    @Test
    fun `addGenres should add Genres when add in GenresDao called successfully`() = runTest {
        coEvery { genresDao.addGenres(any()) } returns Unit

        genresLocalDataSource.addGenres(listOf(sampleGenre))

        coVerify { genresDao.addGenres(any()) }

    }

}