package com.datasource.local.datasource

import com.datasource.local.dao.MovieSimilarDao
import com.repository.movie.models.local.MovieSimilarEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class MovieSimilarLocalDataSourceImplTest {

    private lateinit var movieSimilarLocalDataSourceImpl: MovieSimilarLocalDataSourceImp
    private lateinit var movieSimilarEntity: List<MovieSimilarEntity>
    private lateinit var movieSimilarDao: MovieSimilarDao
    private val language = "en"
    private val page = 1

    @BeforeEach
    fun setUp(){
        movieSimilarDao = mockk(relaxed = true)
        movieSimilarLocalDataSourceImpl = MovieSimilarLocalDataSourceImp(
            movieSimilarDao
        )
        movieSimilarEntity = listOf(MovieSimilarEntity(
            id = 1,
            movieId = 30,
            title = "spider man",
            voteAverage = 5.5,
            posterPath = "www.image.com",
            releaseDate = "2025-05-05",
            language = language,
            page = page
        ))
    }

    @Test
    fun `addSimilarMovies should add movie when addSimilarMovies in movieSimilarDao is called`() = runTest {
        movieSimilarLocalDataSourceImpl.addSimilarMovies(movieSimilarEntity)

        coVerify(exactly = 1) { movieSimilarDao.addSimilarMovies(movieSimilarEntity) }
    }

    @Test
    fun `getSimilarMovies should call getSimilarMovies on DAO and return its result`() = runTest {
        val tvShowId = 2
        coEvery { movieSimilarDao.getSimilarMovies(tvShowId,page,language) } returns movieSimilarEntity

        val result = movieSimilarLocalDataSourceImpl.getSimilarMovies(tvShowId,page,language)

        coVerify { movieSimilarDao.getSimilarMovies(tvShowId,page,language) }
        assert(result == movieSimilarEntity)
    }

    @Test
    fun `getSimilarMovies should return null when DAO returns null`() = runTest {
        val tvShowId = 3
        coEvery { movieSimilarDao.getSimilarMovies(tvShowId,page,language) } returns emptyList()

        val result = movieSimilarLocalDataSourceImpl.getSimilarMovies(tvShowId,page,language)

        coVerify { movieSimilarDao.getSimilarMovies(tvShowId,page,language) }
        assert(result.isEmpty())

    }

}