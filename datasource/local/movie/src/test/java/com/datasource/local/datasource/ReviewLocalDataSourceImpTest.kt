package com.datasource.local.datasource

import com.datasource.local.dao.ReviewDao
import com.repository.entity.ReviewEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ReviewLocalDataSourceImpTest {
    private lateinit var reviewLocalDataSourceImp: ReviewLocalDataSourceImp
    private lateinit var reviewDao: ReviewDao
    private lateinit var sampleReview: ReviewEntity

    @BeforeEach
    fun setUp() {
        reviewDao = mockk(relaxed = true)
        reviewLocalDataSourceImp = ReviewLocalDataSourceImp(reviewDao)
        sampleReview = ReviewEntity(
            id = "1",
            movieId = 2,
            name = "الاسطوره",
            createdAt = LocalDate(year = 2020, month = 2, day = 2),
            avatarUrl = "path",
            username = "username",
            rating = 1.9,
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
            coEvery { reviewDao.getReviewsByMovieId(movieId) } returns listOf(sampleReview)

            val result = reviewLocalDataSourceImp.getReviewsForMovie(movieId)

            coVerify { reviewDao.getReviewsByMovieId(movieId) }
            assert(result == listOf(sampleReview))
        }

    @Test
    fun `getReviewsForMovie should return empty list when DAO returns null`() = runTest {
        val movieId = 3
        coEvery { reviewDao.getReviewsByMovieId(movieId) } returns emptyList()

        val result = reviewLocalDataSourceImp.getReviewsForMovie(movieId)

        coVerify { reviewDao.getReviewsByMovieId(movieId) }
        assert(result.isEmpty())
    }
}