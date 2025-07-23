package com.datasource.local.datasource

import com.datasource.local.dao.TvShowDao
import com.repository.model.local.GenreEntity
import com.repository.model.local.ProductionCompanyEntity
import com.repository.model.local.TvShowEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class TvShowLocalDataSourceImpTest {
    private lateinit var tvShowLocalDataSourceImp: TvShowLocalDataSourceImp
    private lateinit var tvShowDao: TvShowDao


    @BeforeEach
    fun setUp() {
        tvShowDao = mockk(relaxed = true)
        tvShowLocalDataSourceImp = TvShowLocalDataSourceImp(tvShowDao)

    }

    @Test
    fun `addTvShow should add tv show when addTvShow in TvShowDao is called`() = runTest {
        //Given
        tvShowLocalDataSourceImp.addTvShow(sampleTvShow)

        //When&Then
        coVerify(exactly = 1) { tvShowDao.addTvShow(sampleTvShow) }
    }

    @Test
    fun `getTvShowId should call getTvShowById on DAO and return its result`() = runTest {
        //Given
        val tvShowId = 1
        coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns sampleTvShow

        //When
        val result = tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

        //Then
        assertEquals(sampleTvShow, result)
    }

    @Test
    fun `getTvShowId should verify DAO is called with correct ID when it returns TvShowEntity`() =
        runTest {
            //Given
            val tvShowId = 1
            coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns sampleTvShow

            //When
            tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

            //Then
            coVerify(exactly = 1) { tvShowDao.getTvShowById(tvShowId, language) }
        }

    @Test
    fun `getTvShowId should return null when DAO returns null`() = runTest {
        //Given
        val tvShowId = 2
        coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns null

        //When
        val result = tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

        //Then
        assertNull(result)

    }

    @Test
    fun `getTvShowId should verify DAO is called with correct ID when it returns null`() = runTest {
        //Given
        val tvShowId = 2
        coEvery { tvShowDao.getTvShowById(tvShowId, language) } returns null

        //When
        tvShowLocalDataSourceImp.getTvShowId(tvShowId, language)

        //Then
        coVerify(exactly = 1) { tvShowDao.getTvShowById(tvShowId, language) }
    }

    private companion object {
        val language = "en"
        val sampleTvShow = TvShowEntity(
            id = 101,
            title = "Stranger Things",
            voteAverage = 8.7,
            description = "A group of young friends witness supernatural forces and secret government exploits.",
            posterPath = "/stranger_things_poster.jpg",
            genres = listOf(
                GenreEntity(id = 1, name = "Drama"),
                GenreEntity(id = 2, name = "Fantasy"),
                GenreEntity(id = 3, name = "Horror")
            ),
            releaseDate = "2016-07-15",
            runtime = 50,
            country = "Egypt",
            productionCompanies = listOf(
                ProductionCompanyEntity(
                    id = 1001,
                    logoPath = "/netflix_logo.png",
                    name = "Netflix",
                    originCountry = "US"
                ),
                ProductionCompanyEntity(
                    id = 1002,
                    logoPath = "/21laps_logo.png",
                    name = "21 Laps Entertainment",
                    originCountry = "US"
                )
            ),
            seasons = listOf(),
            language = language
        )
    }
}