package com.datasource.local.datasource


import com.datasource.local.dao.MovieReviewDao
import com.repository.movie.models.local.ReviewEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ReviewLocalDataSourceImpTest {
    private lateinit var reviewLocalDataSourceImp: MovieReviewLocalDataSourceImp
    private lateinit var reviewDao: MovieReviewDao

    @BeforeEach
    fun setUp() {
        reviewDao = mockk(relaxed = true)
        reviewLocalDataSourceImp = MovieReviewLocalDataSourceImp(reviewDao)
    }

    @Test
    fun `addReview should add review when addReview in ReviewDao is called`() = runTest {
        reviewLocalDataSourceImp.addReview(listOf(sampleReview))

        coVerify(exactly = 1) { reviewDao.addReviews(listOf(sampleReview)) }
    }

    @Test
    fun `getReviewsForMovie should return list of reviews when DAO returns data`() = runTest {
        val movieId = 2
        coEvery { reviewDao.getReviewsByMovieId(movieId, language) } returns listOf(sampleReview)

        val result = reviewLocalDataSourceImp.getReviewsForMovie(movieId, language)

        assert(result == listOf(sampleReview))
    }

    @Test
    fun `getReviewsForMovie should call DAO once when reviews are returned`() = runTest {
        val movieId = 2
        coEvery { reviewDao.getReviewsByMovieId(movieId, language) } returns listOf(sampleReview)

        reviewLocalDataSourceImp.getReviewsForMovie(movieId, language)

        coVerify(exactly = 1) { reviewDao.getReviewsByMovieId(movieId, language) }
    }

    @Test
    fun `getReviewsForMovie should return empty list when DAO returns empty list`() = runTest {
        val movieId = 3
        coEvery { reviewDao.getReviewsByMovieId(movieId, language) } returns emptyList()

        val result = reviewLocalDataSourceImp.getReviewsForMovie(movieId, language)

        assert(result.isEmpty())
    }

    @Test
    fun `getReviewsForMovie should call DAO once when DAO returns empty list`() = runTest {
        val movieId = 3
        coEvery { reviewDao.getReviewsByMovieId(movieId, language) } returns emptyList()

        reviewLocalDataSourceImp.getReviewsForMovie(movieId, language)

        coVerify(exactly = 1) { reviewDao.getReviewsByMovieId(movieId, language) }
    }


    private companion object{
        val language ="ar"
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