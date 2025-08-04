package com.datasource.remote.movie

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.google.common.truth.Truth.assertThat
import com.repository.movie.models.remote.RatingDto
import com.repository.movie.models.remote.RatingResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsRemoteDataSourceImplTest {
    private lateinit var movieDetailsRemoteDataSourceImpl: MovieDetailsRemoteDataSourceImpl
    private lateinit var retrofitMovieDetailsApiService: RetrofitMovieDetailsApiService

    @Before
    fun setup() {
        retrofitMovieDetailsApiService = mockk(relaxed = true)
        movieDetailsRemoteDataSourceImpl =
            MovieDetailsRemoteDataSourceImpl(retrofitMovieDetailsApiService)
    }

    @Test
    fun `addRatingToMovie should return false when status code is not 1 or 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 3.0f
        val responseDto = RatingResponseDto(statusCode = 10, statusMessage = "Failed")

        coEvery {
            retrofitMovieDetailsApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When
        val result = movieDetailsRemoteDataSourceImpl.addRatingToMovie(movieId, rating)

        // Then
        assertThat(result).isFalse()
    }


    @Test
    fun `addRatingToMovie should complete successfully when status code is 1`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.5f
        val responseDto = RatingResponseDto(1, "Success")

        coEvery {
            retrofitMovieDetailsApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When / Then (should not throw)
        movieDetailsRemoteDataSourceImpl.addRatingToMovie(movieId, rating)
    }

    @Test
    fun `addRatingToMovie should complete successfully when status code is 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.5f
        val responseDto = RatingResponseDto(12, "Success")

        coEvery {
            retrofitMovieDetailsApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When / Then (should not throw)
        movieDetailsRemoteDataSourceImpl.addRatingToMovie(movieId, rating)
    }

    @Test
    fun `getMovieDetails should return movie details when API call is successful`() = runTest {
        // Given
        val movieId = 123
        val language = "en"

        // When
        coEvery {
            retrofitMovieDetailsApiService.getMovieDetails(
                movieId,
                language
            )
        } returns movieDetails

        // Then
        val result = movieDetailsRemoteDataSourceImpl.getMovieDetails(movieId, language)
        assertThat(result).isEqualTo(movieDetails)
    }

    @Test
    fun `getMovieImages should return movie images when API call is successful`() = runTest {
        // Given
        val movieId = 123

        // When
        coEvery {
            retrofitMovieDetailsApiService.getMovieImages(movieId)
        } returns movieImages

        // Then
        val result = movieDetailsRemoteDataSourceImpl.getMovieImages(movieId)
        assertThat(result).isEqualTo(movieImages)
    }

    @Test
    fun `getMovieReviews should return movie review when API call is successful`() = runTest {
        // Given
        val movieId = 123
        val language = "en"
        val page = 1

        // When
        coEvery {
            retrofitMovieDetailsApiService.getMovieReviews(movieId, page, language)
        } returns movieReview

        // Then
        val result = movieDetailsRemoteDataSourceImpl.getMovieReviews(movieId, page, language)
        assertThat(result).isEqualTo(movieReview)
    }

    @Test
    fun `getSimilarMovies should return movie similar when API call is successful`() = runTest {
        // Given
        val movieId = 123
        val language = "en"
        val page = 1

        // When
        coEvery {
            retrofitMovieDetailsApiService.getSimilarMovies(movieId, page, language)
        } returns movieSimilarDto

        // Then
        val result = movieDetailsRemoteDataSourceImpl.getSimilarMovies(movieId, page, language)
        assertThat(result).isEqualTo(movieSimilarDto)
    }

    @Test
    fun `getMovieCredits should return movie credits when API call is successful`() = runTest {
        // Given
        val movieId = 123
        val language = "en"

        // When
        coEvery {
            retrofitMovieDetailsApiService.getMovieCredits(movieId, language)
        } returns movieCreditsDto

        // Then
        val result = movieDetailsRemoteDataSourceImpl.getMovieCredits(movieId, language)
        assertThat(result).isEqualTo(movieCreditsDto)
    }

    @Test
    fun `getTrailerVideoForMovie should return movie video trailer details when API call is successful`() =
        runTest {
            // Given
            val movieId = 123

            // When
            coEvery {
                retrofitMovieDetailsApiService.getTrailerVideoForMovie(movieId)
            } returns movieVideoDto

            // Then
            val result =
                movieDetailsRemoteDataSourceImpl.getTrailerVideoForMovie(movieId)
            assertThat(result).isEqualTo(movieVideoDto)
        }

}