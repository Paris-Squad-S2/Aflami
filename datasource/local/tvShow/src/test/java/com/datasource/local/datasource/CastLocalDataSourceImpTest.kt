package com.datasource.local.datasource


import com.datasource.local.dao.TvShowCastDao
import com.repository.model.local.CastEntity
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class CastLocalDataSourceImpTest {
    private lateinit var castLocalDataSourceImp: TvShowCastLocalDataSourceImp
    private lateinit var castDao: TvShowCastDao


    @BeforeEach
    fun setUp() {
        castDao = mockk(relaxed = true)
        castLocalDataSourceImp = TvShowCastLocalDataSourceImp(castDao)
    }

    @Test
    fun `addCast should add cast when addCast in CastDao called successfully`() = runTest {
        //Given
        castLocalDataSourceImp.addCast(listOf(sampleCast, sampleCast2))

        //When&Then
        coVerify(exactly = 1) { castLocalDataSourceImp.addCast(listOf(sampleCast, sampleCast2)) }
    }

    @Test
    fun `getCastByMovieId should call getCastByTvShowId on DAO and return its result`() = runTest {
        //Given
        val tvShowId = 10
        every { runBlocking { castDao.getCastByTvShowId(tvShowId,language) } } returns sampleCastList

        //When
        val result = castLocalDataSourceImp.getCastByTvShowId(tvShowId,language)

        //Then
        assertEquals(sampleCastList, result)
    }

    @Test
    fun `getCastByTvShowId should call DAO method`() = runTest {
        //Given
        val tvShowId = 10
        every { runBlocking { castDao.getCastByTvShowId(tvShowId, language) } } returns sampleCastList

        //When
        castLocalDataSourceImp.getCastByTvShowId(tvShowId, language)

        //Then
        coVerify(exactly = 1) { castDao.getCastByTvShowId(tvShowId, language) }
    }


    @Test
    fun `getCastByMovieId should return empty list if DAO returns empty list`() = runTest {
        //Given
        val tvShowIdWithNoCast = 200
        val emptyList = emptyList<CastEntity>()
        every { runBlocking { castDao.getCastByTvShowId(tvShowIdWithNoCast, language) } } returns emptyList

        //When
        val result = castLocalDataSourceImp.getCastByTvShowId(tvShowIdWithNoCast,language)

        //Then
        assertEquals(emptyList, result)
    }

    @Test
    fun `getCastByTvShowId should call DAO method when cast list is empty`() = runTest {
        //Given
        val tvShowIdWithNoCast = 200
        every { runBlocking { castDao.getCastByTvShowId(tvShowIdWithNoCast, language) } } returns emptyList()

        //When
        castLocalDataSourceImp.getCastByTvShowId(tvShowIdWithNoCast, language)

        //Then
        coVerify(exactly = 1) { castDao.getCastByTvShowId(tvShowIdWithNoCast, language) }
    }


    private companion object{
        val language ="en"
       val  sampleCast = CastEntity(
        id = 1,
        tvShowId = 2,
        name = "name",
        imageUri = "path",
        language = language
        )
        val sampleCast2 = CastEntity(
        id = 2,
        tvShowId = 4,
        name = "Maze",
        imageUri = "path",
        language = language
        )
        val sampleCastList = listOf(sampleCast, sampleCast2)
    }
}