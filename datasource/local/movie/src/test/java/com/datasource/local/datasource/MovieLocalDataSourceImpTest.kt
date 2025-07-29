package com.datasource.local.datasource

import androidx.work.WorkManager
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
    private var workManager: WorkManager = mockk(relaxed = true)
    private lateinit var movieDao: MovieDao


    @BeforeEach
    fun setUp() {
        movieDao = mockk(relaxed = true)
        movieLocalDataSource = MovieLocalDataSourceImp(workManager,movieDao)
    }


    @Test
    fun `addMovie should add movie when addMovie in MovieDao called successfully`() = runTest {
        //Given
        coEvery { movieDao.addMovies(any()) } returns Unit

        //When
        movieLocalDataSource.addMovie(sampleMovie)

        //Then
        coVerify(exactly = 1) { movieDao.addMovies(sampleMovie) }

    }

    @Test
    fun `getMovieById should return movie when DAO returns movie`() = runTest {
        //Given
        val movieId = 2
        coEvery { movieDao.getMovieById(movieId, language) } returns sampleMovie

        //When
        val result = movieLocalDataSource.getMovieById(movieId, language)

        //Then
        assert(result == sampleMovie)
    }

    @Test
    fun `getMovieById should call DAO once when movie exists`() = runTest {
        //Given
        val movieId = 2
        coEvery { movieDao.getMovieById(movieId, language) } returns sampleMovie

        //When
        movieLocalDataSource.getMovieById(movieId, language)

        //Then
        coVerify(exactly = 1) { movieDao.getMovieById(movieId, language) }
    }

    @Test
    fun `getMovieById should return null when DAO returns null`() = runTest {
        //Given
        val movieId = 3
        coEvery { movieDao.getMovieById(movieId, language) } returns null

        //When
        val result = movieLocalDataSource.getMovieById(movieId, language)

        //Then
        assert(result == null)
    }

    @Test
    fun `getMovieById should call DAO once when movie is null`() = runTest {
        //Given
        val movieId = 3
        coEvery { movieDao.getMovieById(movieId, language) } returns null

        //When
        movieLocalDataSource.getMovieById(movieId, language)

        //Then
        coVerify(exactly = 1) { movieDao.getMovieById(movieId, language) }
    }


    private companion object {
        val language = "ar"
        val sampleMovie = MovieEntity(
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
            ),
            language = language
        )
    }

}