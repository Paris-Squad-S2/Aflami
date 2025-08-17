package com.repository.media

import com.google.common.truth.Truth.assertThat
import com.repository.media.services.RetrofitTvShowDetailsApiService
import com.repository.model.remote.EpisodeVideoDto
import com.repository.model.remote.EpisodeVideoResultDto
import com.repository.model.remote.RemoveTvRatingDto
import com.repository.movie.models.remote.RatingDto
import com.repository.movie.models.remote.RatingResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
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
    fun `addRatingToTvShow should return true when status code is 1`() = runTest {
        // Given
        val movieId = 123
        val rating = 8.0f
        val response = RatingResponseDto(statusCode = 1, statusMessage = "Success")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(movieId, RatingDto(rating))
        } returns response

        // When
        val result = tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `addRatingToTvShow should return true when status code is 12`() = runTest {
        // Given
        val movieId = 456
        val rating = 7.5f
        val response = RatingResponseDto(statusCode = 12, statusMessage = "Updated")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(movieId, RatingDto(rating))
        } returns response

        // When
        val result = tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `addRatingToTvShow should return false when status code is not 1 or 12`() = runTest {
        // Given
        val movieId = 789
        val rating = 6.0f
        val response = RatingResponseDto(statusCode = 10, statusMessage = "Not authorized")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(movieId, RatingDto(rating))
        } returns response

        // When
        val result = tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `addRatingToTvShow should throw exception when API call fails`() = runTest {
        // Given
        val movieId = 999
        val rating = 9.0f

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(movieId, RatingDto(rating))
        } throws RuntimeException("Server error")

        // Then
        Assert.assertThrows(RuntimeException::class.java) {
            runTest {
                tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)
            }
        }
    }

    @Test
    fun `getTvShowDetails should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowDetails(
                tvShowId,
                language
            )
        } throws RuntimeException("API error")
        Assert.assertThrows(RuntimeException::class.java) {
            runTest { tvShowDetailsRemoteDataSourceImpl.getTvShowDetails(tvShowId, language) }
        }
    }

    @Test
    fun `getTvShowImages should throw when API throws exception`() = runTest {
        val tvShowId = -1
        coEvery { retrofitTvShowDetailsApiService.getTvShowImages(tvShowId) } throws RuntimeException(
            "API error"
        )
        Assert.assertThrows(RuntimeException::class.java) {
            runTest { tvShowDetailsRemoteDataSourceImpl.getTvShowImages(tvShowId) }
        }
    }

    @Test
    fun `getTvShowReviews should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val page = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowReviews(
                tvShowId,
                page,
                language
            )
        } throws RuntimeException("API error")
        Assert.assertThrows(RuntimeException::class.java) {
            runTest { tvShowDetailsRemoteDataSourceImpl.getTvShowReviews(tvShowId, page, language) }
        }
    }

    @Test
    fun `getSimilarTvShows should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val page = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getSimilarTvShows(
                tvShowId,
                page,
                language
            )
        } throws RuntimeException("API error")
        Assert.assertThrows(RuntimeException::class.java) {
            runTest {
                tvShowDetailsRemoteDataSourceImpl.getSimilarTvShows(
                    tvShowId,
                    page,
                    language
                )
            }
        }
    }

    @Test
    fun `getTvShowCredits should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowCredits(
                tvShowId,
                language
            )
        } throws RuntimeException("API error")
        Assert.assertThrows(RuntimeException::class.java) {
            runTest { tvShowDetailsRemoteDataSourceImpl.getTvShowCredits(tvShowId, language) }
        }
    }

    @Test
    fun `getSeasonDetails should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val seasonNumber = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getSeasonDetails(
                tvShowId,
                seasonNumber,
                language
            )
        } throws RuntimeException("API error")
        Assert.assertThrows(RuntimeException::class.java) {
            runTest {
                tvShowDetailsRemoteDataSourceImpl.getSeasonDetails(
                    tvShowId,
                    seasonNumber,
                    language
                )
            }
        }
    }

    @Test
    fun `getTrailerVideoForTvShow should throw when API throws exception`() = runTest {
        val tvShowId = -1
        coEvery { retrofitTvShowDetailsApiService.getTrailerVideoForTvShow(tvShowId) } throws RuntimeException(
            "API error"
        )
        Assert.assertThrows(RuntimeException::class.java) {
            runTest { tvShowDetailsRemoteDataSourceImpl.getTrailerVideoForTvShow(tvShowId) }
        }
    }

    @Test
    fun `getTrailerVideoForEpisode should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val seasonNumber = -1
        val episodeNumber = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getTrailerVideoForEpisode(
                tvShowId,
                seasonNumber,
                episodeNumber,
                language
            )
        } throws RuntimeException("API error")
        Assert.assertThrows(RuntimeException::class.java) {
            runTest {
                tvShowDetailsRemoteDataSourceImpl.getTrailerVideoForEpisode(
                    tvShowId,
                    seasonNumber,
                    episodeNumber,
                    language
                )
            }
        }
    }
    @Test
    fun `addRatingToTvShow should complete successfully when status code is 1`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.5f
        val response = RatingResponseDto(1, "Success")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(
                movieId,
                RatingDto(rating)
            )
        } returns response

        // When (should not throw)
        tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)
    }

    @Test
    fun `addRatingToTvShow should complete successfully when status code is 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 4.0f
        val response = RatingResponseDto(12, "Success")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(
                movieId,
                RatingDto(rating)
            )
        } returns response

        // When (should not throw)
        tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)
    }

    @Test
    fun `addRatingToTvShow should throw exception when status code is not 1 or 12`() = runTest {
        // Given
        val movieId = 123
        val rating = 3.5f
        val response = RatingResponseDto(10, "Invalid session")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(
                movieId,
                RatingDto(rating)
            )
        } returns response

        // Then
        Assert.assertThrows(Exception::class.java) {
            runTest {
                tvShowDetailsRemoteDataSourceImpl.addRatingToTvShow(movieId, rating)
            }
        }
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

    @Test
    fun `getTrailerVideoForEpisode should deliver epic trailer when the API nails the audition`() = runTest {
        val tvShowId = 456
        val seasonNumber = 2
        val episodeNumber = 3
        val language = "en-US"
        val blockbusterTrailer = EpisodeVideoDto(
            id = 123,
            episodeVideoResultDto = listOf(
                EpisodeVideoResultDto(
                    id = "trailer123",
                    iso31661 = "US",
                    iso6391 = "en",
                    key = "trailer_key",
                    name = "Epic Trailer for the Ages",
                    official = true,
                    publishedAt = "2024-06-01T12:00:00Z",
                    site = "YouTube",
                    size = 1080,
                    type = "Trailer"
                )
            )
        )

        coEvery {
            retrofitTvShowDetailsApiService.getTrailerVideoForEpisode(
                tvShowId,
                seasonNumber,
                episodeNumber,
                language
            )
        } returns blockbusterTrailer

        val result = tvShowDetailsRemoteDataSourceImpl.getTrailerVideoForEpisode(
            tvShowId,
            seasonNumber,
            episodeNumber,
            language
        )
        assertThat(result).isEqualTo(blockbusterTrailer)
    }

    @Test
    fun `deleteTvShowRating should return true when status code is 13`() = runTest {
        // Given
        val tvShowId = 123
        val response = RemoveTvRatingDto(
            status_code = 13,
            status_message = "Deleted successfully",
            success = true
        )

        coEvery {
            retrofitTvShowDetailsApiService.deleteTvShowRating(tvShowId)
        } returns response

        // When
        val result = tvShowDetailsRemoteDataSourceImpl.deleteTvShowRating(tvShowId)

        // Then
        assertThat(result).isTrue()
    }

    @Test
    fun `deleteTvShowRating should return false when status code is not 13`() = runTest {
        // Given
        val tvShowId = 456
        val response =
            RemoveTvRatingDto(status_code = 10, status_message = "Not authorized", success = false)

        coEvery {
            retrofitTvShowDetailsApiService.deleteTvShowRating(tvShowId)
        } returns response

        // When
        val result = tvShowDetailsRemoteDataSourceImpl.deleteTvShowRating(tvShowId)

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `deleteTvShowRating should throw exception when API call fails`() = runTest {
        // Given
        val tvShowId = 789

        coEvery {
            retrofitTvShowDetailsApiService.deleteTvShowRating(tvShowId)
        } throws RuntimeException("Server error")

        // Then
        Assert.assertThrows(RuntimeException::class.java) {
            runTest {
                tvShowDetailsRemoteDataSourceImpl.deleteTvShowRating(tvShowId)
            }
        }
    }


}