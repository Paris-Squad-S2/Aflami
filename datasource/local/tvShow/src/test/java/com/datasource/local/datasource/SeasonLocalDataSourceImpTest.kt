package com.datasource.local.datasource

import com.datasource.local.dao.SeasonDao
import com.repository.model.local.EpisodeEntity
import com.repository.model.local.SeasonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SeasonLocalDataSourceImpTest {
    private lateinit var seasonLocalDataSourceImp: TvShowSeasonLocalDataSourceImp
    private lateinit var seasonDao: SeasonDao

    @BeforeEach
    fun setUp() {
        seasonDao = mockk(relaxed = true)
        seasonLocalDataSourceImp = TvShowSeasonLocalDataSourceImp(seasonDao)
    }

    @Test
    fun `addSeason should add season when addSeason in SeasonDao called successfully`() = runTest {
        //Given
        seasonLocalDataSourceImp.addSeasonDetails(sampleSeason)

        //When&Then
        coVerify(exactly = 1) { seasonLocalDataSourceImp.addSeasonDetails(sampleSeason) }
    }

    @Test
    fun `getSeasonsByTvShowId should call getSeasonByTvShowId on DAO and return its result`() =
        runTest {
            //Given
            val tvShowId = 10
            val seasonNumber = 1
            coEvery { seasonDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber) } returns sampleSeason

            //When
            val result = seasonLocalDataSourceImp.getSeasonDetailsByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)
            //Then
            assert(result == sampleSeason)
        }

    @Test
    fun `getSeasonByTvShowId should verify DAO is called with correct ID`() = runTest {
        // Given
        val tvShowId = 10
        val seasonNumber = 1
        coEvery { seasonDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber) } returns sampleSeason

        // When
        seasonLocalDataSourceImp.getSeasonDetailsByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 1) { seasonDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber) }
    }

    @Test
    fun `getSeasonsByTvShowId should return null when DAO returns null`() = runTest {
        //Given
        val tvShowId = 1
        val seasonNumber = 1
        coEvery { seasonDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber) } returns null

        //When
        val result = seasonLocalDataSourceImp.getSeasonDetailsByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

        //Then
        assert(result == null)
    }

    @Test
    fun `getSeasonDetailsByTvShowId should verify DAO is called when DAO returns null`() = runTest {
        // Given
        val tvShowId = 1
        val seasonNumber = 1
        coEvery { seasonDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber) } returns null

        // When
        seasonLocalDataSourceImp.getSeasonDetailsByTvShowIdAndSeasonNumber(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 1) { seasonDao.getSeasonByTvShowIdAndSeasonNumber(tvShowId, seasonNumber) }
    }

    private companion object {
        val sampleSeason = SeasonEntity(
            id = 1,
            tvShowId = 10,
            name = "The Red Wedding",
            episodes = listOf(
                EpisodeEntity(
                    id = 1,
                    episodeNumber = 2,
                    posterUrl = "path",
                    voteAverage = 9.9,
                    airDate = LocalDate(year = 2020, month = 1, day = 1),
                    runtime = 20,
                    description = "GGGGGG",
                    stillUrl = "path"
                )
            ),
            episodeCount = 0,
            seasonNumber = 1
        )
    }
}