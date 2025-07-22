package com.datasource.local.datasource


import com.datasource.local.dao.TvShowReviewDao
import com.google.common.truth.Truth.assertThat
import com.repository.model.local.ReviewEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class ReviewLocalDataSourceImpTest {
    private lateinit var reviewLocalDataSourceImp: TvShowReviewLocalDataSourceImp
    private lateinit var reviewDao: TvShowReviewDao

    @BeforeEach
    fun setUp() {
        reviewDao = mockk(relaxed = true)
        reviewLocalDataSourceImp = TvShowReviewLocalDataSourceImp(reviewDao)
    }

    @Test
    fun `addReview should add review when addReview in ReviewDao is called`() = runTest {
        //Given
        reviewLocalDataSourceImp.addReview(listOf(sampleReview))

        //When&Then
        coVerify(exactly = 1) { reviewDao.addReviews(listOf(sampleReview)) }
    }

    @Test
    fun `getReviewsByTvShowId should return list of reviews from DAO`() =
        runTest {
            //Given
            val tvShowId = 2
            coEvery { reviewDao.getReviewsByTvShowId(tvShowId, language) } returns listOf(
                sampleReview
            )

            //When
            val result = reviewLocalDataSourceImp.getReviewsByTvShowId(tvShowId, language)

            //Then
            assert(result == listOf(sampleReview))
        }


    @Test
    fun `getReviewsByTvShowId should verify DAO is called`() = runTest {
        // GIVEN
        val tvShowId = 2
        coEvery { reviewDao.getReviewsByTvShowId(tvShowId, language) } returns listOf(sampleReview)

        // WHEN
        reviewLocalDataSourceImp.getReviewsByTvShowId(tvShowId, language)

        // THEN
        coVerify(exactly = 1) { reviewDao.getReviewsByTvShowId(tvShowId, language) }
    }

    @Test
    fun `getReviewsByTvShowId should return empty list when DAO returns empty list`() = runTest {
        // GIVEN
        val tvShowId = 3
        coEvery { reviewDao.getReviewsByTvShowId(tvShowId, language) } returns emptyList()

        // WHEN
        val result = reviewLocalDataSourceImp.getReviewsByTvShowId(tvShowId, language)

        // THEN
        assertThat(result).isEmpty()
    }

    @Test
    fun `getReviewsByTvShowId should verify DAO is called when returning empty list`() = runTest {
        // GIVEN
        val tvShowId = 3
        coEvery { reviewDao.getReviewsByTvShowId(tvShowId, language) } returns emptyList()

        // WHEN
        reviewLocalDataSourceImp.getReviewsByTvShowId(tvShowId, language)

        // THEN
        coVerify(exactly = 1) { reviewDao.getReviewsByTvShowId(tvShowId, language) }
    }


    private companion object {
        val language = "en"
        val sampleReview = ReviewEntity(
            id = 1,
            tvShowId = 2,
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