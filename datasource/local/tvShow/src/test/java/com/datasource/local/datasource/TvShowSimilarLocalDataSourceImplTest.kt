package com.datasource.local.datasource

import com.datasource.local.dao.TvShowSimilarDao
import com.repository.model.local.TvShowSimilarEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class TvShowSimilarLocalDataSourceImplTest {

    private lateinit var tvShowSimilarLocalDataSourceImpl: TvShowSimilarLocalDataSourceImpl
    private lateinit var tvShowSimilarDao: TvShowSimilarDao


    @BeforeEach
    fun setUp() {
        tvShowSimilarDao = mockk(relaxed = true)
        tvShowSimilarLocalDataSourceImpl = TvShowSimilarLocalDataSourceImpl(
            tvShowSimilarDao
        )

    }

    @Test
    fun `addSimilarTvShows should add tv show when addSimilarTvShows in tvShowSimilarDao is called`() =
        runTest {
            //Given
            tvShowSimilarLocalDataSourceImpl.addSimilarTvShows(tvShowSimilarEntity)

            //When&Then
            coVerify(exactly = 1) { tvShowSimilarDao.addSimilarTvShows(tvShowSimilarEntity) }
        }

    @Test
    fun `getSimilarTvShows should call getSimilarTvShows on DAO and return its result`() = runTest {
        //Given
        val tvShowId = 2
        coEvery {
            tvShowSimilarDao.getSimilarTvShows(
                tvShowId,
                page,
                language
            )
        } returns tvShowSimilarEntity

        //When
        val result = tvShowSimilarLocalDataSourceImpl.getSimilarTvShows(tvShowId, page, language)

        //Then
        assert(result == tvShowSimilarEntity)
    }

    @Test
    fun `getSimilarTvShows should call DAO with correct parameters`() = runTest {
        // Given
        val tvShowId = 2
        coEvery {
            tvShowSimilarDao.getSimilarTvShows(tvShowId, page, language)
        } returns tvShowSimilarEntity

        // When
        tvShowSimilarLocalDataSourceImpl.getSimilarTvShows(tvShowId, page, language)

        // Then
        coVerify(exactly = 1) {
            tvShowSimilarDao.getSimilarTvShows(tvShowId, page, language)
        }
    }


    @Test
    fun `getTvShowId should return null when DAO returns null`() = runTest {
        //Given
        val tvShowId = 3
        coEvery { tvShowSimilarDao.getSimilarTvShows(tvShowId, page, language) } returns emptyList()

        //When
        val result = tvShowSimilarLocalDataSourceImpl.getSimilarTvShows(tvShowId, page, language)

        //Then
        assert(result.isEmpty())

    }

    @Test
    fun `getTvShowId should verify DAO is called when it returns empty list`() = runTest {
        // Given
        val tvShowId = 3
        coEvery { tvShowSimilarDao.getSimilarTvShows(tvShowId, page, language) } returns emptyList()

        // When
        tvShowSimilarLocalDataSourceImpl.getSimilarTvShows(tvShowId, page, language)

        // Then
        coVerify(exactly = 1) {
            tvShowSimilarDao.getSimilarTvShows(tvShowId, page, language)
        }
    }


    private companion object {
        val language = "en"
        val page = 1

        val tvShowSimilarEntity = listOf(
            TvShowSimilarEntity(
                id = 1,
                tvShowId = 30,
                title = "spider man",
                voteAverage = 5.5,
                posterPath = "www.image.com",
                releaseDate = "2025-05-05",
                language = language,
                page = page
            )
        )
    }

}