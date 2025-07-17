package com.datasource.local.datasource

import com.datasource.local.dao.SeasonDao
import com.repository.entity.EpisodeEntity
import com.repository.entity.SeasonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SeasonLocalDataSourceImpTest {
    private lateinit var seasonLocalDataSourceImp: SeasonLocalDataSourceImp
    private lateinit var seasonDao: SeasonDao
    private lateinit var sampleSeason: SeasonEntity

    @BeforeEach
    fun setUp() {
        seasonDao = mockk(relaxed = true)
        seasonLocalDataSourceImp = SeasonLocalDataSourceImp(seasonDao)
        sampleSeason = SeasonEntity(
            id = "1",
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
            )
        )
    }

    @Test
    fun `addSeason should add season when addSeason in SeasonDao called successfully`() = runTest {
        seasonLocalDataSourceImp.addSeason(listOf(sampleSeason))

        coVerify(exactly = 1) { seasonLocalDataSourceImp.addSeason(listOf(sampleSeason)) }
    }

    @Test
    fun `getSeasonsByTvShowId should call getSeasonByTvShowId on DAO and return its result`() =
        runTest {
            val tvShowId = 10
            coEvery { seasonDao.getSeasonByTvShowId(tvShowId) } returns listOf(sampleSeason)

            val result = seasonLocalDataSourceImp.getSeasonsByTvShowId(tvShowId)

            coVerify { seasonDao.getSeasonByTvShowId(tvShowId) }
            assert(result == listOf(sampleSeason))
        }

    @Test
    fun `getSeasonsByTvShowId should return null when DAO returns null`() = runTest {
        val tvShowId = 1
        coEvery { seasonDao.getSeasonByTvShowId(tvShowId) } returns null

        val result = seasonLocalDataSourceImp.getSeasonsByTvShowId(tvShowId)

        coVerify { seasonDao.getSeasonByTvShowId(tvShowId) }
        assert(result == null)
    }

}