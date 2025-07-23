package com.datasource.remote.tvShow

import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TvShowDetailsRemoteDataSourceImplTest {
    private lateinit var tvShowDetailsRemoteDataSourceImpl: TvShowDetailsRemoteDataSourceImpl
    private lateinit var retrofitTvShowDetailsApiService: RetrofitTvShowDetailsApiService

    @Before
    fun setup() {
        retrofitTvShowDetailsApiService = mockk(relaxed = true)
        tvShowDetailsRemoteDataSourceImpl =
            TvShowDetailsRemoteDataSourceImpl(retrofitTvShowDetailsApiService)
    }

    @Test
    fun `getTvShowDetails should return tv show details when API call is successful`() = runTest {
        // Given
        val tvShowId = 123
        val language = "en"

        // When
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowDetails(
                tvShowId,
                language
            )
        } returns tvShowDetails

        // Then
        val result = tvShowDetailsRemoteDataSourceImpl.getTvShowDetails(tvShowId, language)
        assertThat(result).isEqualTo(tvShowDetails)
    }

    @Test
    fun `getTvShowImages should return tv show images when API call is successful`() = runTest {
        // Given
        val tvShowId = 123

        // When
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowImages(tvShowId)
        } returns tvShowImages

        // Then
        val result = tvShowDetailsRemoteDataSourceImpl.getTvShowImages(tvShowId)
        assertThat(result).isEqualTo(tvShowImages)
    }

    @Test
    fun `getTvShowReviews should return tv show review when API call is successful`() = runTest {
        // Given
        val tvShowId = 123
        val language = "en"
        val page = 1

        // When
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowReviews(tvShowId, page, language)
        } returns tvShowReview

        // Then
        val result = tvShowDetailsRemoteDataSourceImpl.getTvShowReviews(tvShowId, page, language)
        assertThat(result).isEqualTo(tvShowReview)
    }

    @Test
    fun `getSimilarTvShows should return tv show similar when API call is successful`() = runTest {
        // Given
        val tvShowId = 123
        val language = "en"
        val page = 1

        // When
        coEvery {
            retrofitTvShowDetailsApiService.getSimilarTvShows(tvShowId, page, language)
        } returns tvShowSimilarDto

        // Then
        val result = tvShowDetailsRemoteDataSourceImpl.getSimilarTvShows(tvShowId, page, language)
        assertThat(result).isEqualTo(tvShowSimilarDto)
    }

    @Test
    fun `getTvShowCredits should return tv show credits when API call is successful`() = runTest {
        // Given
        val tvShowId = 123
        val language = "en"

        // When
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowCredits(tvShowId, language)
        } returns tvShowCreditsDto

        // Then
        val result = tvShowDetailsRemoteDataSourceImpl.getTvShowCredits(tvShowId, language)
        assertThat(result).isEqualTo(tvShowCreditsDto)
    }

    @Test
    fun `getSeasonDetails should return tv show season details when API call is successful`() =
        runTest {
            // Given
            val tvShowId = 123
            val seasonNumber = 1
            val language = "en"

            // When
            coEvery {
                retrofitTvShowDetailsApiService.getSeasonDetails(tvShowId, seasonNumber, language)
            } returns tvShowSeasonDetails

            // Then
            val result =
                tvShowDetailsRemoteDataSourceImpl.getSeasonDetails(tvShowId, seasonNumber, language)
            assertThat(result).isEqualTo(tvShowSeasonDetails)
        }

    @Test
    fun `getTrailerVideoForTvShow should return tv show video trailer details when API call is successful`() =
        runTest {
            // Given
            val tvShowId = 123

            // When
            coEvery {
                retrofitTvShowDetailsApiService.getTrailerVideoForTvShow(tvShowId)
            } returns tvShowVideoDto

            // Then
            val result =
                tvShowDetailsRemoteDataSourceImpl.getTrailerVideoForTvShow(tvShowId)
            assertThat(result).isEqualTo(tvShowVideoDto)
        }

}