package com.datasource.local.datasource


import com.datasource.local.dao.MovieCastDao
import com.repository.movie.models.local.CastEntity
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class CastLocalDataSourceImpTest {
    private lateinit var castLocalDataSourceImp: MovieCastLocalDataSourceImp
    private lateinit var castDao: MovieCastDao

    @BeforeEach
    fun setUp() {
        castDao = mockk(relaxed = true)
        castLocalDataSourceImp = MovieCastLocalDataSourceImp(castDao)
    }

    @Test
    fun `addCast should add cast when addCast in CastDao called successfully`() = runTest {
        castLocalDataSourceImp.addCast(listOf(sampleCast, sampleCast2))

        coVerify(exactly = 1) { castLocalDataSourceImp.addCast(listOf(sampleCast, sampleCast2)) }
    }

    @Test
    fun `getCastByMovieId should return the correct result from DAO`() = runTest {
        val movieId = 10
        every { runBlocking { castDao.getCastByMovieId(movieId, "ar") } } returns sampleCastList

        val result = castLocalDataSourceImp.getCastByMovieId(movieId, "ar")

        assertEquals(sampleCastList, result)
    }

    @Test
    fun `getCastByMovieId should call DAO exactly once`() = runTest {
        val movieId = 10
        every { runBlocking { castDao.getCastByMovieId(movieId, "ar") } } returns sampleCastList

        castLocalDataSourceImp.getCastByMovieId(movieId, "ar")

        coVerify(exactly = 1) { castDao.getCastByMovieId(movieId, "ar") }
    }

    @Test
    fun `getCastByMovieId should return empty list when DAO returns empty list`() = runTest {
        val movieId = 200
        val emptyList = emptyList<CastEntity>()
        every { runBlocking { castDao.getCastByMovieId(movieId, "ar") } } returns emptyList

        val result = castLocalDataSourceImp.getCastByMovieId(movieId, "ar")

        assertEquals(emptyList, result)
    }

    @Test
    fun `getCastByMovieId should verify DAO called for empty result`() = runTest {
        val movieId = 200
        every { runBlocking { castDao.getCastByMovieId(movieId, "ar") } } returns emptyList()

        castLocalDataSourceImp.getCastByMovieId(movieId, "ar")

        coVerify(exactly = 1) { castDao.getCastByMovieId(movieId, "ar") }
    }

    companion object{
       val  sampleCast = CastEntity(
        id = 1,
        movieId = 2,
        name = "name",
        imageUri = "path",
        language = "ar"
        )
        val sampleCast2 = CastEntity(
        id = 2,
        movieId = 4,
        name = "Maze",
        imageUri = "path",
        language = "ar"
        )
        val sampleCastList = listOf(sampleCast, sampleCast2)
    }
}