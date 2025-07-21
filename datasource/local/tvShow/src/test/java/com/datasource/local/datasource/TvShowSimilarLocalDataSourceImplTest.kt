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
    private lateinit var tvShowSimilarEntity: List<TvShowSimilarEntity>
    private lateinit var tvShowSimilarDao: TvShowSimilarDao
    private val language = "en"
    private val page = 1

    @BeforeEach
    fun setUp(){
        tvShowSimilarDao = mockk(relaxed = true)
        tvShowSimilarLocalDataSourceImpl = TvShowSimilarLocalDataSourceImpl(
            tvShowSimilarDao
        )
        tvShowSimilarEntity = listOf(TvShowSimilarEntity(
            id = 1,
            tvShowId = 30,
            title = "spider man",
            voteAverage = 5.5,
            posterPath = "www.image.com",
            releaseDate = "2025-05-05",
            language = language,
            page = page
        ))
    }

    @Test
    fun `addSimilarTvShows should add tv show when addSimilarTvShows in tvShowSimilarDao is called`() = runTest {
        tvShowSimilarLocalDataSourceImpl.addSimilarTvShows(tvShowSimilarEntity)

        coVerify(exactly = 1) { tvShowSimilarDao.addSimilarTvShows(tvShowSimilarEntity) }
    }

    @Test
    fun `getSimilarTvShows should call getSimilarTvShows on DAO and return its result`() = runTest {
        val tvShowId = 2
        coEvery { tvShowSimilarDao.getSimilarTvShows(tvShowId,page,language) } returns tvShowSimilarEntity

        val result = tvShowSimilarLocalDataSourceImpl.getSimilarTvShows(tvShowId,page,language)

        coVerify { tvShowSimilarDao.getSimilarTvShows(tvShowId,page,language) }
        assert(result == tvShowSimilarEntity)
    }

    @Test
    fun `getTvShowId should return null when DAO returns null`() = runTest {
        val tvShowId = 3
        coEvery { tvShowSimilarDao.getSimilarTvShows(tvShowId,page,language) } returns emptyList()

        val result = tvShowSimilarLocalDataSourceImpl.getSimilarTvShows(tvShowId,page,language)

        coVerify { tvShowSimilarDao.getSimilarTvShows(tvShowId,page,language) }
        assert(result.isEmpty())

    }

}