package com.example.tvshow.repository

import com.example.tvshow.dataSource.remote.TvShowDetailsRemoteDataSource
import com.example.tvshow.mapper.toEntity
import com.example.tvshow.model.remote.TvShowCastDto
import com.example.tvshow.model.remote.TvShowCreditsDto
import com.example.tvshow.model.remote.TvShowDto
import com.example.tvshow.model.remote.TvShowImagesDto
import com.example.tvshow.model.remote.TvShowLogoDto
import com.example.tvshow.model.remote.TvShowProductionCompanyDto
import com.example.tvshow.model.remote.TvShowReviewDto
import com.example.tvshow.model.remote.TvShowReviewsDto
import com.example.tvshow.model.remote.TvShowSeasonDto
import com.example.tvshow.model.remote.TvShowSimilarDto
import com.example.tvshow.model.remote.TvShowSimilarsDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TvShowRepositoryImplTest {
    private val tvShowDetailsRemoteDataSource = mockk<TvShowDetailsRemoteDataSource>()
    private val repository = TvShowRepositoryImpl(tvShowDetailsRemoteDataSource)

    @Test
    fun `getTvShowDetails - should return tv show details when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 550
        val language = "en"
        val mockTvShowDto = TvShowDto(
            id = tvShowId,
            name = "The Epic Blockbuster",
            overview = "Mind-blowing action sequences",
            firstAirDate = "2024-07-15"
        )
        val expectedTvShow = mockTvShowDto.toEntity()

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(
                tvShowId,
                language
            )
        } returns mockTvShowDto
        // When
        val result = repository.getTvShowDetails(tvShowId)

        // Then
        assertEquals(expectedTvShow.title, result.title)
    }

    @Test
    fun `getTvShowCast - should return tv show cast when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 550
        val language = "en"
        val mockTvShowCreditsDto = TvShowCreditsDto(
            id = tvShowId,
            cast = listOf(
                TvShowCastDto(
                    name = "alex"
                )
            )
        )
        val expectedTvShowCast = mockTvShowCreditsDto.cast?.map { it.toEntity() } ?: emptyList()

        // When
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(
                tvShowId,
                language
            )
        } returns mockTvShowCreditsDto
        val result = repository.getTvShowCast(tvShowId)

        // Then
        assertEquals(expectedTvShowCast, result)

    }

    @Test
    fun `getTvShowRecommendations - should return tv show recommendations when API delivers the goods`() =
        runTest {
            // Given
            val tvShowId = 123
            val page = 1
            val language = "en"

            val mockTvShowSimilarsDto = TvShowSimilarsDto(
                tvShowSimilarDto = listOf(
                    TvShowSimilarDto(
                        title = "batman"
                    )
                )
            )
            val expectedCast =
                mockTvShowSimilarsDto.tvShowSimilarDto?.map { it.toEntity() } ?: emptyList()

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getSimilarTvShows(
                    tvShowId,
                    page,
                    language
                )
            } returns mockTvShowSimilarsDto
            val result = repository.getTvShowRecommendations(tvShowId, page)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getTvShowGallery - should return tv show gallery when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 123

        val mockTvShowImagesDto = TvShowImagesDto(
            id = tvShowId,
            logos = listOf(
                TvShowLogoDto(filePath = "http://images.jpeg")
            )
        )
        val expectedCast = mockTvShowImagesDto.logos?.map { it.toEntity(tvShowId) } ?: emptyList()

        // When
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowImages(
                tvShowId
            )
        } returns mockTvShowImagesDto
        val result = repository.getTvShowGallery(tvShowId).images

        // Then
        assertEquals(expectedCast, result)
    }

    @Test
    fun `getCompanyProducts - should return company products when API delivers the goods`() =
        runTest {
            // Given
            val tvShowId = 123
            val language = "en"

            val mockTvShowImagesDto = listOf(
                TvShowProductionCompanyDto(
                    name = "sonic"
                )
            )

            val expectedCast = mockTvShowImagesDto.map { it.toEntity() }

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowDetails(
                    tvShowId, language
                ).productionCompanies
            } returns mockTvShowImagesDto

            val result = repository.getCompanyProducts(tvShowId)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getTvShowReview - should return tv show review when API delivers the goods`() =
        runTest {
            // Given
            val tvShowId = 123
            val language = "en"
            val page = 1

            val mockTvShowReviewsDto = TvShowReviewsDto(
                results = listOf(
                    TvShowReviewDto(
                        author = "mohammed"
                    )
                )
            )

            val expectedCast = mockTvShowReviewsDto.results?.map { it.toEntity() }

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowReviews(
                    tvShowId, page, language
                )
            } returns mockTvShowReviewsDto

            val result = repository.getTvShowReview(tvShowId,page)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getSeasonDetails - should return season details when API delivers the goods`() =
        runTest {
            // Given
            val tvShowId = 123
            val language = "en"
            val seasonNumber = 1

            val mockTvShowReviewsDto = TvShowSeasonDto(
                name = "stronger things"
            )

            val expectedCast = mockTvShowReviewsDto.toEntity()

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getSeasonDetails(
                    tvShowId, seasonNumber, language
                )
            } returns mockTvShowReviewsDto

            val result = repository.getSeasonDetails(tvShowId,seasonNumber)

            // Then
            assertEquals(expectedCast, result)
        }

}