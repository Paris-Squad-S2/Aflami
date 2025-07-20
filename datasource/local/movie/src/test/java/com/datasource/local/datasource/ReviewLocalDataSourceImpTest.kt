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
    private lateinit var sampleReview: ReviewEntity

    @BeforeEach
    fun setUp() {
        reviewDao = mockk(relaxed = true)
        reviewLocalDataSourceImp = MovieReviewLocalDataSourceImp(reviewDao)
        sampleReview = ReviewEntity(
            id = 1,
            movieId = 2,
            name = "الاسطوره",
            createdAt = LocalDate(year = 2020, month = 2, day = 2),
            avatarUrl = "path",
            username = "username",
            rating = 1.9,
            description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White.",
            language = "ar"
        )
    }

    @Test
    fun `addReview should add review when addReview in ReviewDao is called`() = runTest {
        reviewLocalDataSourceImp.addReview(listOf(sampleReview))

        coVerify(exactly = 1) { reviewDao.addReviews(listOf(sampleReview)) }
    }

    @Test
    fun `getReviewsForMovie should call getReviewsByMovieId on DAO and return its result`() =
        runTest {
            val movieId = 2
            coEvery { reviewDao.getReviewsByMovieId(movieId,"ar") } returns listOf(sampleReview)

            val result = reviewLocalDataSourceImp.getReviewsForMovie(movieId,"ar")

            coVerify { reviewDao.getReviewsByMovieId(movieId,"ar") }
            assert(result == listOf(sampleReview))
        }

    @Test
    fun `getReviewsForMovie should return empty list when DAO returns null`() = runTest {
        val movieId = 3
        coEvery { reviewDao.getReviewsByMovieId(movieId,"ar") } returns emptyList()

        val result = reviewLocalDataSourceImp.getReviewsForMovie(movieId,"ar")

        coVerify { reviewDao.getReviewsByMovieId(movieId,"ar") }
        assert(result.isEmpty())
    }
}