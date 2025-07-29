package com.datasource.remote.tvShow

import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.google.common.truth.Truth.assertThat
import com.repository.model.remote.EpisodeVideoDto
import com.repository.model.remote.EpisodeVideoResultDto
import com.repository.movie.models.remote.RatingDto
import com.repository.movie.models.remote.RatingResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
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
    fun `getTvShowDetails should throw when API throws exception`() = runTest {
        val tvShowId = -1
        val language = ""
        coEvery {
            retrofitTvShowDetailsApiService.getTvShowDetails(
                tvShowId,
                language
            )
        } throws RuntimeException("API error")
        assertThrows(RuntimeException::class.java) {
            runTest { tvShowDetailsRemoteDataSourceImpl.getTvShowDetails(tvShowId, language) }
        }
    }

    @Test
    fun `getTvShowImages should throw when API throws exception`() = runTest {
        val tvShowId = -1
        coEvery { retrofitTvShowDetailsApiService.getTvShowImages(tvShowId) } throws RuntimeException(
            "API error"
        )
        assertThrows(RuntimeException::class.java) {
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
        assertThrows(RuntimeException::class.java) {
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
        assertThrows(RuntimeException::class.java) {
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
        assertThrows(RuntimeException::class.java) {
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
        assertThrows(RuntimeException::class.java) {
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
        assertThrows(RuntimeException::class.java) {
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
        assertThrows(RuntimeException::class.java) {
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
        val response = RatingResponseDto(status_code = 1, status_message = "Success")

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
        val response = RatingResponseDto(status_code = 12, status_message = "Success")

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
        val response = RatingResponseDto(status_code = 10, status_message = "Invalid session")

        coEvery {
            retrofitTvShowDetailsApiService.addRatingToTvShow(
                movieId,
                RatingDto(rating)
            )
        } returns response

        // Then
        assertThrows(Exception::class.java) {
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

}
