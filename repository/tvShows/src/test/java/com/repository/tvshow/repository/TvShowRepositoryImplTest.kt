package com.repository.tvshow.repository

import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toEntity
import com.repository.mapper.toLocalDto
import com.repository.model.local.GalleryEntity
import com.repository.model.remote.TvShowSeasonDto
import com.repository.repository.TvShowRepositoryImpl
import com.repository.tvshow.testUtils.mockTvShowCreditsDto
import com.repository.tvshow.testUtils.mockTvShowDto
import com.repository.tvshow.testUtils.mockTvShowImagesDto
import com.repository.tvshow.testUtils.mockTvShowLogoDto
import com.repository.tvshow.testUtils.mockTvShowReviewsDto
import com.repository.tvshow.testUtils.mockTvShowSimilarsDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class TvShowRepositoryImplTest {
    private lateinit var tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource
    private lateinit var tvShowLocalDataSource: TvShowLocalDataSource
    private lateinit var tvShowSeasonLocalDataSource: TvShowSeasonLocalDataSource
    private lateinit var tvShowReviewLocalDataSource: TvShowReviewLocalDataSource
    private lateinit var tvShowGalleryLocalDataSource: TvShowGalleryLocalDataSource
    private lateinit var tvShowCastLocalDataSource: TvShowCastLocalDataSource
    private lateinit var tvShowRepository: TvShowRepositoryImpl

    @BeforeEach
    fun setUp() {
        tvShowDetailsRemoteDataSource = mockk<TvShowDetailsRemoteDataSource>()
        tvShowLocalDataSource = mockk<TvShowLocalDataSource>()
        tvShowSeasonLocalDataSource = mockk<TvShowSeasonLocalDataSource>()
        tvShowReviewLocalDataSource = mockk<TvShowReviewLocalDataSource>()
        tvShowGalleryLocalDataSource = mockk<TvShowGalleryLocalDataSource>()
        tvShowCastLocalDataSource = mockk<TvShowCastLocalDataSource>()

        tvShowRepository = TvShowRepositoryImpl(
            tvShowLocalDataSource,
            tvShowSeasonLocalDataSource,
            tvShowReviewLocalDataSource,
            tvShowGalleryLocalDataSource,
            tvShowCastLocalDataSource,
            tvShowDetailsRemoteDataSource
        )
    }

    @Test
    fun `getTvShowDetails - should return tv show details when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 550
        val language = "en"

        val expectedTvShow = mockTvShowDto.toEntity()

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
        } returns mockTvShowDto

        coEvery {
            tvShowLocalDataSource.getTvShowId(tvShowId)
        } returns mockTvShowDto.toLocalDto()

        coEvery {
            tvShowLocalDataSource.addTvShow(any())
        } returns Unit

        // When
        val result = tvShowRepository.getTvShowDetails(tvShowId)

        // Then
        assertEquals(expectedTvShow.title, result.title)
    }

    @Test
    fun `getTvShowCast - should return tv show cast when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 550
        val language = "en"
        val expectedTvShowCast = mockTvShowCreditsDto.cast?.map { it.toEntity() } ?: emptyList()

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        } returns mockTvShowCreditsDto

        coEvery {
            tvShowCastLocalDataSource.getCastByTvShowId(tvShowId)
        } returns expectedTvShowCast.map { it.toLocalDto() }

        coEvery {
            tvShowCastLocalDataSource.addCast(any())
        } returns Unit

        // When
        val result = tvShowRepository.getTvShowCast(tvShowId)

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
            val result = tvShowRepository.getTvShowRecommendations(tvShowId, page)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getTvShowGallery - should return tv show gallery when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 123

        val expectedImages = mockTvShowLogoDto.logos?.map { it.toEntity(tvShowId) } ?: emptyList()

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
        } returns mockTvShowLogoDto

        coEvery {
            tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)
        } returns GalleryEntity(
            id = 0,
            tvShowId = 123,
            images = expectedImages.map { it.toLocalDto() },
        )

        coEvery {
            tvShowGalleryLocalDataSource.addGallery(any())
        } returns Unit

        // When
        val result = tvShowRepository.getTvShowGallery(tvShowId).images

        // Then
        assertEquals(expectedImages, result)
    }

    @Test
    fun `getCompanyProducts - should return company products when API delivers the goods`() =
        runTest {
            // Given
            val tvShowId = 123
            val language = "en"

            val expectedCast = mockTvShowImagesDto.map { it.toEntity() }

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowDetails(
                    tvShowId, language
                ).productionCompanies
            } returns mockTvShowImagesDto

            val result = tvShowRepository.getCompanyProducts(tvShowId)

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

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowReviews(
                    tvShowId, page, language
                )
            } returns mockTvShowReviewsDto

            val dto =
                mockTvShowReviewsDto.results?.map { it.toEntity().toLocalDto() } ?: emptyList()
            coEvery {
                tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId)
            } returns dto

            coEvery {
                tvShowReviewLocalDataSource.addReview(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowReview(tvShowId, page)

            // Then
            val expectedReviews = mockTvShowReviewsDto.results?.map { it.toEntity() } ?: emptyList()
            assertEquals(expectedReviews.first().createdAt, result.first().createdAt)
        }

    @Test
    fun `getSeasonDetails - should return season details when API delivers the goods`() =
        runTest {
            // Given
            val tvShowId = 123
            val language = "en"
            val seasonNumber = 1

            val mockTvShowSeasonDto = TvShowSeasonDto(
                name = "stronger things"
            )

            val expectedSeason = mockTvShowSeasonDto.toEntity()

            coEvery {
                tvShowDetailsRemoteDataSource.getSeasonDetails(
                    tvShowId, seasonNumber, language
                )
            } returns mockTvShowSeasonDto

            coEvery {
                tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
            } returns mockTvShowSeasonDto.toLocalDto()

            coEvery {
                tvShowSeasonLocalDataSource.addSeasonDetails(any())
            } returns Unit

            // When
            val result = tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

            // Then
            assertEquals(expectedSeason.name, result.name)
        }


}