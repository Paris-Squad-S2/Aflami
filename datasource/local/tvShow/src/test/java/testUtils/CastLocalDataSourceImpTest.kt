package testUtils

import com.datasource.local.dao.CastDao
import com.datasource.local.datasource.CastLocalDataSourceImp
import com.repository.entity.CastEntity

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class CastLocalDataSourceImpTest {
    private lateinit var castLocalDataSourceImp: CastLocalDataSourceImp
    private lateinit var castDao: CastDao
    private lateinit var sampleCast: CastEntity
    private lateinit var sampleCast2: CastEntity
    private lateinit var sampleCastList: List<CastEntity>

    @BeforeEach
    fun setUp() {
        castDao = mockk(relaxed = true)
        castLocalDataSourceImp = CastLocalDataSourceImp(castDao)
        sampleCast = CastEntity(
            id = 1,
            tvShowId = 2,
            name = "name",
            imageUri = "path"
        )
        sampleCast2 = CastEntity(
            id = 2,
            tvShowId = 4,
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
    fun `getCastByMovieId should call getCastByTvShowId on DAO and return its result`() = runTest {
        val tvShowId = 10
        every { runBlocking { castDao.getCastByTvShowId(tvShowId) } } returns sampleCastList // For MockK with suspend functions

        val result = castLocalDataSourceImp.getCastByTvShowId(tvShowId)

        coVerify(exactly = 1) { castDao.getCastByTvShowId(tvShowId) }
        assertEquals(sampleCastList, result)
    }


    @Test
    fun `getCastByMovieId should return empty list if DAO returns empty list`() = runTest {
        val tvShowIdWithNoCast = 200
        val emptyList = emptyList<CastEntity>()
        every { runBlocking { castDao.getCastByTvShowId(tvShowIdWithNoCast) } } returns emptyList

        val result = castLocalDataSourceImp.getCastByTvShowId(tvShowIdWithNoCast)

        coVerify(exactly = 1) { castDao.getCastByTvShowId(tvShowIdWithNoCast) }
        assertEquals(emptyList, result)
    }
}