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
    private lateinit var sampleCast: CastEntity
    private lateinit var sampleCast2: CastEntity
    private lateinit var sampleCastList: List<CastEntity>

    @BeforeEach
    fun setUp() {
        castDao = mockk(relaxed = true)
        castLocalDataSourceImp = MovieCastLocalDataSourceImp(castDao)
        sampleCast = CastEntity(
            id = 1,
            movieId = 2,
            name = "name",
            imageUri = "path"
        )
        sampleCast2 = CastEntity(
            id = 2,
            movieId = 4,
            name = "Maze",
            imageUri = "path"
        )
        sampleCastList = listOf(sampleCast, sampleCast2)
    }

    @Test
    fun `addCast should add cast when addCast in CastDao called successfully`() = runTest {
        castLocalDataSourceImp.addCast(listOf(sampleCast, sampleCast2))

        coVerify(exactly = 1) { castLocalDataSourceImp.addCast(listOf(sampleCast, sampleCast2)) }
    }

    @Test
    fun `getCastByMovieId should call getCastByMovieId on DAO and return its result`() = runTest {
        val movieId = 10
        every { runBlocking { castDao.getCastByMovieId(movieId) } } returns sampleCastList // For MockK with suspend functions

        val result = castLocalDataSourceImp.getCastByMovieId(movieId)

        coVerify(exactly = 1) { castDao.getCastByMovieId(movieId) }
        assertEquals(sampleCastList, result)
    }


    @Test
    fun `getCastByMovieId should return empty list if DAO returns empty list`() = runTest {
        val movieIdWithNoCast = 200
        val emptyList = emptyList<CastEntity>()
        every { runBlocking { castDao.getCastByMovieId(movieIdWithNoCast) } } returns emptyList

        val result = castLocalDataSourceImp.getCastByMovieId(movieIdWithNoCast)

        coVerify(exactly = 1) { castDao.getCastByMovieId(movieIdWithNoCast) }
        assertEquals(emptyList, result)
    }
}