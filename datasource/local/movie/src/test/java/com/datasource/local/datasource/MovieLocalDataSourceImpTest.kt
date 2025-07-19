package com.datasource.local.datasource

import com.datasource.local.dao.MovieDao
import com.repository.movie.models.local.GenreEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.ProductionCompanyEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class MovieLocalDataSourceImpTest {
    private lateinit var movieLocalDataSource: MovieLocalDataSourceImp
    private lateinit var movieDao: MovieDao
    private lateinit var sampleMovie: MovieEntity
    private lateinit var sampleMovie2: MovieEntity

    @BeforeEach
    fun setUp() {
        movieDao = mockk(relaxed = true)
        movieLocalDataSource = MovieLocalDataSourceImp(movieDao)
        sampleMovie = MovieEntity(
            id = 1,
            title = "Inception",
            voteAverage = 8.8,
            description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea.",
            posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            genres = listOf(
                GenreEntity(id = 28, name = "Action"),
                GenreEntity(id = 878, name = "Science Fiction"),
                GenreEntity(id = 12, name = "Adventure")
            ),
            releaseDate = "2010-07-16",
            runtime = 148,
            country = "Egypt",
            productionCompanies = listOf(
                ProductionCompanyEntity(
                    id = 923,
                    logoPath = "/5UQsZrfbfG2dYJbx8DxfoTr2xYh.png",
                    name = "Legendary Pictures",
                    originCountry = "US"
                ),
                ProductionCompanyEntity(
                    id = 9996,
                    logoPath = "/3T19XSr6yqaLNkD2RY2zwnYQhjq.png",
                    name = "Syncopy",
                    originCountry = "GB"
                )
            )
        )
        sampleMovie2 = MovieEntity(
            id = 2,
            title = "Inception",
            voteAverage = 8.8,
            description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea.",
            posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            genres = listOf(
                GenreEntity(id = 28, name = "Action"),
                GenreEntity(id = 878, name = "Science Fiction"),
                GenreEntity(id = 12, name = "Adventure")
            ),
            releaseDate = "2010-07-16",
            runtime = 148,
            country = "US",
            productionCompanies = listOf(
                ProductionCompanyEntity(
                    id = 923,
                    logoPath = "/5UQsZrfbfG2dYJbx8DxfoTr2xYh.png",
                    name = "Legendary Pictures",
                    originCountry = "US"
                ),
                ProductionCompanyEntity(
                    id = 9996,
                    logoPath = "/3T19XSr6yqaLNkD2RY2zwnYQhjq.png",
                    name = "Syncopy",
                    originCountry = "GB"
                )
            )
        )
    }


    @Test
    fun `addMovie should add movie when addMovie in MovieDao called successfully`() = runTest {
        movieLocalDataSource.addMovie(sampleMovie)

        coVerify(exactly = 1) { movieLocalDataSource.addMovie(sampleMovie) }

    }

    @Test
    fun `getMovieById should call getMovie on DAO and return its result`() = runTest {
        val movieId = 2
        coEvery { movieDao.getMovieById(movieId) } returns sampleMovie

        val result = movieLocalDataSource.getMovieById(movieId)

        coVerify { movieDao.getMovieById(movieId) }
        assert(result == sampleMovie)
    }

    @Test
    fun `getMovieById should return null when DAO returns null`() = runTest {
        val movieId = 3
        coEvery { movieDao.getMovieById(movieId) } returns null

        val result = movieLocalDataSource.getMovieById(movieId)

        coVerify { movieDao.getMovieById(movieId) }
        assert(result == null)
    }

}