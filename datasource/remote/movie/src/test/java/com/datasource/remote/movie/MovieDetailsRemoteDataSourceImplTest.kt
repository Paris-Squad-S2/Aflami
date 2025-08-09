package com.datasource.remote.movie

import com.datasource.remote.movie.service.MovieApiService
import com.google.common.truth.Truth.assertThat
import com.repository.movie.models.remote.RatingDto
import com.repository.movie.models.remote.RatingResponseDto
import com.repository.movie.models.remote.RemoveRatingDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsRemoteDataSourceImplTest {
    private lateinit var movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl
    private lateinit var movieApiService: MovieApiService

    @Before
    fun setup() {
        movieApiService = mockk(relaxed = true)
        movieRemoteDataSourceImpl =
            MovieRemoteDataSourceImpl(movieApiService)
    }

    @Test
    fun `addRatingToMovie should return false when status code is not 1 or 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 3.0f
        val responseDto = RatingResponseDto(statusCode = 10, statusMessage = "Failed")

        coEvery {
            movieApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When
        val result = movieRemoteDataSourceImpl.addRatingToMovie(movieId, rating)

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
            movieApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When / Then (should not throw)
        movieRemoteDataSourceImpl.addRatingToMovie(movieId, rating)
    }

    @Test
    fun `addRatingToMovie should complete successfully when status code is 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.5f
        val responseDto = RatingResponseDto(12, "Success")

        coEvery {
            movieApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When / Then (should not throw)
        movieRemoteDataSourceImpl.addRatingToMovie(movieId, rating)
    }

    @Test
    fun `getMovieDetails should return movie details when API call is successful`() = runTest {
        // Given
        val movieId = 123
        val language = "en"

        // When
        coEvery {
            movieApiService.getMovieDetails(
                movieId,
                language
            )
        } returns movieDetails

        // Then
        val result = movieRemoteDataSourceImpl.getMovieDetails(movieId, language)
        assertThat(result).isEqualTo(movieDetails)
    }

    @Test
    fun `getMovieImages should return movie images when API call is successful`() = runTest {
        // Given
        val movieId = 123

        // When
        coEvery {
            movieApiService.getMovieImages(movieId)
        } returns movieImages

        // Then
        val result = movieRemoteDataSourceImpl.getMovieImages(movieId)
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
            movieApiService.getMovieReviews(movieId, page, language)
        } returns movieReview

        // Then
        val result = movieRemoteDataSourceImpl.getMovieReviews(movieId, page, language)
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
            movieApiService.getSimilarMovies(movieId, page, language)
        } returns movieSimilarDto

        // Then
        val result = movieRemoteDataSourceImpl.getSimilarMovies(movieId, page, language)
        assertThat(result).isEqualTo(movieSimilarDto)
    }

    @Test
    fun `getMovieCredits should return movie credits when API call is successful`() = runTest {
        // Given
        val movieId = 123
        val language = "en"

        // When
        coEvery {
            movieApiService.getMovieCredits(movieId, language)
        } returns movieCreditsDto

        // Then
        val result = movieRemoteDataSourceImpl.getMovieCredits(movieId, language)
        assertThat(result).isEqualTo(movieCreditsDto)
    }

    @Test
    fun `getTrailerVideoForMovie should return movie video trailer details when API call is successful`() =
        runTest {
            // Given
            val movieId = 123

            // When
            coEvery {
                movieApiService.getTrailerVideoForMovie(movieId)
            } returns movieVideoDto

            // Then
            val result =
                movieRemoteDataSourceImpl.getTrailerVideoForMovie(movieId)
            assertThat(result).isEqualTo(movieVideoDto)
        }

    @Test
    fun `deleteMovieRating should return true when API returns success true`() = runTest {
        // Given
        val movieId = 123
        val responseDto = mockk<RemoveRatingDto> {
            coEvery { success } returns true
        }
        coEvery { movieApiService.deleteMovieRating(movieId) } returns responseDto

        // When
        val result = movieRemoteDataSourceImpl.deleteMovieRating(movieId)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `deleteMovieRating should return false when API returns success false`() = runTest {
        // Given
        val movieId = 123
        val responseDto = mockk<RemoveRatingDto> {
            coEvery { success } returns false
        }
        coEvery { movieApiService.deleteMovieRating(movieId) } returns responseDto

        // When
        val result = movieRemoteDataSourceImpl.deleteMovieRating(movieId)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `addRatingToMovie should return false when API returns unexpected status code`() = runTest {
        // Given
        val movieId = 123
        val rating = 2.5f
        val responseDto = RatingResponseDto(statusCode = 33, statusMessage = "Error")
        coEvery {
            movieApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When
        val result = movieRemoteDataSourceImpl.addRatingToMovie(movieId, rating)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `addRatingToMovie should return true when API returns status code 1`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.0f
        val responseDto = RatingResponseDto(statusCode = 1, statusMessage = "Success")
        coEvery {
            movieApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When
        val result = movieRemoteDataSourceImpl.addRatingToMovie(movieId, rating)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `addRatingToMovie should return true when API returns status code 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.0f
        val responseDto = RatingResponseDto(statusCode = 12, statusMessage = "Updated")
        coEvery {
            movieApiService.addRatingToMovie(
                movieId,
                RatingDto(rating)
            )
        } returns responseDto

        // When
        val result = movieRemoteDataSourceImpl.addRatingToMovie(movieId, rating)

        // Then
        assertThat(result).isTrue()
    }

}