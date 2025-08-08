package com.datasource.local.datasource

import androidx.work.WorkManager
import com.datasource.local.dao.MovieDao
import com.google.common.truth.Truth.assertThat
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.GenreEntity
import com.repository.movie.models.local.ImageEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.ProductionCompanyEntity
import com.repository.movie.models.local.ReviewEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class MovieLocalDataSourceImpTest {
    private lateinit var movieLocalDataSource: MovieLocalDataSourceImp
    private var workManager: WorkManager = mockk(relaxed = true)
    private lateinit var movieDao: MovieDao


    @BeforeEach
    fun setUp() {
        movieDao = mockk(relaxed = true)
        movieLocalDataSource = MovieLocalDataSourceImp(workManager, movieDao)
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

    @Test
    fun `addCast should add cast when addCast in CastDao called successfully`() = runTest {
        //Given
        movieLocalDataSource.addMovieCast(listOf(sampleCast, sampleCast2))

        //When&Then
        coVerify(exactly = 1) {
            movieLocalDataSource.addMovieCast(
                listOf(
                    sampleCast,
                    sampleCast2
                )
            )
        }
    }

    @Test
    fun `getCastByMovieId should return the correct result from DAO`() = runTest {
        //Given
        val movieId = 10
        every {
            runBlocking {
                movieDao.getCastByMovieId(
                    movieId,
                    language
                )
            }
        } returns sampleCastList

        //When
        val result = movieLocalDataSource.getCastByMovieId(
            movieId,
            language
        )

        //Then
        assertEquals(sampleCastList, result)
    }

    @Test
    fun `getCastByMovieId should call DAO exactly once`() = runTest {
        //Given
        val movieId = 10
        every {
            runBlocking {
                movieDao.getCastByMovieId(
                    movieId,
                    language
                )
            }
        } returns sampleCastList

        //When
        movieLocalDataSource.getCastByMovieId(
            movieId,
            language
        )

        //Then
        coVerify(exactly = 1) {
            movieDao.getCastByMovieId(
                movieId,
                language
            )
        }
    }

    @Test
    fun `getCastByMovieId should return empty list when DAO returns empty list`() = runTest {
        //Given
        val movieId = 200
        val emptyList = emptyList<CastEntity>()
        every {
            runBlocking {
                movieDao.getCastByMovieId(
                    movieId,
                    language
                )
            }
        } returns emptyList

        //When
        val result = movieLocalDataSource.getCastByMovieId(
            movieId,
            language
        )

        //Then
        assertEquals(emptyList, result)
    }

    @Test
    fun `getCastByMovieId should verify DAO called for empty result`() = runTest {
        //Given
        val movieId = 200
        every {
            runBlocking {
                movieDao.getCastByMovieId(
                    movieId,
                    language
                )
            }
        } returns emptyList()

        //When
        movieLocalDataSource.getCastByMovieId(
            movieId,
            language
        )

        //Then
        coVerify(exactly = 1) {
            movieDao.getCastByMovieId(
                movieId,
                language
            )
        }
    }

    @Test
    fun `should add gallery when addGallery is called`() = runTest {
        //Given
        movieLocalDataSource.addMovieGallery(sampleGallery)

        //When&Then
        coVerify(exactly = 1) { movieDao.addMovieGallery(sampleGallery) }
    }

    @Test
    fun `getGalleryByMovieId should return gallery when DAO returns gallery`() = runTest {
        //Given
        val movieId = 1
        coEvery { movieDao.getGalleryByMovieId(movieId) } returns sampleGallery

        //When
        val result = movieLocalDataSource.getGalleryByMovieId(movieId)

        //Then
        assertThat(result).isEqualTo(sampleGallery)
    }

    @Test
    fun `getGalleryByMovieId should call DAO method exactly once`() = runTest {
        //Given
        val movieId = 1
        coEvery { movieDao.getGalleryByMovieId(movieId) } returns sampleGallery

        //When
        movieLocalDataSource.getGalleryByMovieId(movieId)

        //Then
        coVerify(exactly = 1) { movieDao.getGalleryByMovieId(movieId) }
    }

    @Test
    fun `getGalleryByMovieId should return null when DAO returns null`() = runTest {
        //Given
        val movieId = 2
        coEvery { movieDao.getGalleryByMovieId(movieId) } returns null

        //When
        val result = movieLocalDataSource.getGalleryByMovieId(movieId)

        //Then
        assertThat(result).isNull()
    }

    @Test
    fun `getGalleryByMovieId should call DAO once when returning null`() = runTest {
        //Given
        val movieId = 2
        coEvery { movieDao.getGalleryByMovieId(movieId) } returns null

        //When
        movieLocalDataSource.getGalleryByMovieId(movieId)

        //Then
        coVerify(exactly = 1) { movieDao.getGalleryByMovieId(movieId) }
    }

    @Test
    fun `addSimilarMovies should add movie when addSimilarMovies in movieSimilarDao is called`() =
        runTest {
            //Given
            movieLocalDataSource.addSimilarMovies(movieSimilarEntity)

            //When&Then
            coVerify(exactly = 1) { movieDao.addSimilarMovies(movieSimilarEntity) }
        }

    @Test
    fun `getSimilarMovies should return expected result from DAO`() = runTest {
        //Given
        val tvShowId = 2
        coEvery {
            movieDao.getSimilarMovies(
                tvShowId,
                page,
                language
            )
        } returns movieSimilarEntity

        //When
        val result = movieLocalDataSource.getSimilarMovies(tvShowId, page, language)

        //Then
        assert(result == movieSimilarEntity)
    }

    @Test
    fun `getSimilarMovies should call DAO once when result is not null`() = runTest {
        //Given
        val tvShowId = 2
        coEvery {
            movieDao.getSimilarMovies(
                tvShowId,
                page,
                language
            )
        } returns movieSimilarEntity

        //When
        movieLocalDataSource.getSimilarMovies(tvShowId, page, language)

        //Then
        coVerify(exactly = 1) { movieDao.getSimilarMovies(tvShowId, page, language) }
    }

    @Test
    fun `getSimilarMovies should return empty list when DAO returns empty list`() = runTest {
        //Given
        val tvShowId = 3
        coEvery { movieDao.getSimilarMovies(tvShowId, page, language) } returns emptyList()

        //When
        val result = movieLocalDataSource.getSimilarMovies(tvShowId, page, language)

        //Then
        assert(result.isEmpty())
    }

    @Test
    fun `getSimilarMovies should call DAO once when result is empty`() = runTest {
        //Given
        val tvShowId = 3
        coEvery { movieDao.getSimilarMovies(tvShowId, page, language) } returns emptyList()

        //When
        movieLocalDataSource.getSimilarMovies(tvShowId, page, language)

        //Then
        coVerify(exactly = 1) { movieDao.getSimilarMovies(tvShowId, page, language) }
    }

    @Test
    fun `addReview should add review when addReview in ReviewDao is called`() = runTest {
        //Given
        movieLocalDataSource.addMovieReviews(listOf(sampleReview))

        //When&Then
        coVerify(exactly = 1) { movieDao.addMovieReviews(listOf(sampleReview)) }
    }

    @Test
    fun `getReviewsForMovie should return list of reviews when DAO returns data`() = runTest {
        //Given
        val movieId = 2
        coEvery { movieDao.getReviewsByMovieId(movieId,
            language
        ) } returns listOf(sampleReview)

        //When
        val result = movieLocalDataSource.getReviewsByMovieId(movieId,
            language
        )

        //Then
        assert(result == listOf(sampleReview))
    }

    @Test
    fun `getReviewsForMovie should call DAO once when reviews are returned`() = runTest {
        //Given
        val movieId = 2
        coEvery { movieDao.getReviewsByMovieId(movieId,
            language
        ) } returns listOf(sampleReview)

        //When
        movieLocalDataSource.getReviewsByMovieId(movieId,
            language
        )

        //Then
        coVerify(exactly = 1) { movieDao.getReviewsByMovieId(movieId,
            language
        ) }
    }

    @Test
    fun `getReviewsForMovie should return empty list when DAO returns empty list`() = runTest {
        //Given
        val movieId = 3
        coEvery { movieDao.getReviewsByMovieId(movieId,
            language
        ) } returns emptyList()

        //When
        val result = movieLocalDataSource.getReviewsByMovieId(movieId,
            language
        )

        //Then
        assert(result.isEmpty())
    }

    @Test
    fun `getReviewsForMovie should call DAO once when DAO returns empty list`() = runTest {
        //Given
        val movieId = 3
        coEvery { movieDao.getReviewsByMovieId(movieId,
            language
        ) } returns emptyList()

        //When
        movieLocalDataSource.getReviewsByMovieId(movieId,
            language
        )

        //Then
        coVerify(exactly = 1) { movieDao.getReviewsByMovieId(movieId,
            language
        ) }
    }

    private companion object {
        val language = "ar"
        val page = 1
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

        val sampleCast = CastEntity(
            id = 1,
            movieId = 2,
            name = "name",
            imageUri = "path",
            language = language
        )
        val sampleCast2 = CastEntity(
            id = 2,
            movieId = 4,
            name = "Maze",
            imageUri = "path",
            language = language
        )

        val sampleCastList = listOf(
            sampleCast,
            sampleCast2
        )
        val sampleGallery = GalleryEntity(
            id = 1,
            movieId = 1,
            images = listOf(
                ImageEntity(
                    id = 10,
                    url = "uri"
                ),
                ImageEntity(
                    id = 20,
                    url = "uri"
                ),
            )
        )
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
        val sampleReview = ReviewEntity(
            id = 1,
            movieId = 2,
            name = "الاسطوره",
            createdAt = LocalDate(year = 2020, month = 2, day = 2),
            avatarUrl = "path",
            username = "username",
            rating = 1.9,
            description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White.",
            language = language
        )
    }

}