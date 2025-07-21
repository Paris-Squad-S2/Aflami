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
    private lateinit var movieSimilarDao: MovieSimilarDao


    @BeforeEach
    fun setUp() {
        movieSimilarDao = mockk(relaxed = true)
        movieSimilarLocalDataSourceImpl = MovieSimilarLocalDataSourceImp(
            movieSimilarDao
        )
    }

    @Test
    fun `addSimilarMovies should add movie when addSimilarMovies in movieSimilarDao is called`() =
        runTest {
            //Given
            movieSimilarLocalDataSourceImpl.addSimilarMovies(movieSimilarEntity)

            //When&Then
            coVerify(exactly = 1) { movieSimilarDao.addSimilarMovies(movieSimilarEntity) }
        }

    @Test
    fun `getSimilarMovies should return expected result from DAO`() = runTest {
        //Given
        val tvShowId = 2
        coEvery {
            movieSimilarDao.getSimilarMovies(
                tvShowId,
                page,
                language
            )
        } returns movieSimilarEntity

        //When
        val result = movieSimilarLocalDataSourceImpl.getSimilarMovies(tvShowId, page, language)

        //Then
        assert(result == movieSimilarEntity)
    }

    @Test
    fun `getSimilarMovies should call DAO once when result is not null`() = runTest {
        //Given
        val tvShowId = 2
        coEvery {
            movieSimilarDao.getSimilarMovies(
                tvShowId,
                page,
                language
            )
        } returns movieSimilarEntity

        //When
        movieSimilarLocalDataSourceImpl.getSimilarMovies(tvShowId, page, language)

        //Then
        coVerify(exactly = 1) { movieSimilarDao.getSimilarMovies(tvShowId, page, language) }
    }

    @Test
    fun `getSimilarMovies should return empty list when DAO returns empty list`() = runTest {
        //Given
        val tvShowId = 3
        coEvery { movieSimilarDao.getSimilarMovies(tvShowId, page, language) } returns emptyList()

        //When
        val result = movieSimilarLocalDataSourceImpl.getSimilarMovies(tvShowId, page, language)

        //Then
        assert(result.isEmpty())
    }

    @Test
    fun `getSimilarMovies should call DAO once when result is empty`() = runTest {
        //Given
        val tvShowId = 3
        coEvery { movieSimilarDao.getSimilarMovies(tvShowId, page, language) } returns emptyList()

        //When
        movieSimilarLocalDataSourceImpl.getSimilarMovies(tvShowId, page, language)

        //Then
        coVerify(exactly = 1) { movieSimilarDao.getSimilarMovies(tvShowId, page, language) }
    }


    private companion object {
        val language = "en"
        val page = 1
        val movieSimilarEntity = listOf(
            MovieSimilarEntity(
                id = 1,
                movieId = 30,
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