package com.repository.tvshow.repository

import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toLocalDto
import com.repository.repository.TvShowRepositoryImpl
import com.repository.tvshow.testUtils.mockTvShowCreditsDto
import com.repository.tvshow.testUtils.mockTvShowDto
import com.repository.tvshow.testUtils.mockTvShowSimilarsDto
import com.repository.util.NetworkConnectionChecker
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
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
    private lateinit var tvShowSimilarLocalDataSource: TvShowSimilarLocalDataSource
    private var networkConnectionChecker: NetworkConnectionChecker = mockk(relaxed = true)
    private lateinit var tvShowRepository: TvShowRepositoryImpl

    @BeforeEach
    fun setUp() {
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        tvShowDetailsRemoteDataSource = mockk<TvShowDetailsRemoteDataSource>(relaxed = true)
        tvShowLocalDataSource = mockk<TvShowLocalDataSource>(relaxed = true)
        tvShowSeasonLocalDataSource = mockk<TvShowSeasonLocalDataSource>(relaxed = true)
        tvShowReviewLocalDataSource = mockk<TvShowReviewLocalDataSource>(relaxed = true)
        tvShowGalleryLocalDataSource = mockk<TvShowGalleryLocalDataSource>(relaxed = true)
        tvShowCastLocalDataSource = mockk<TvShowCastLocalDataSource>(relaxed = true)
        tvShowSimilarLocalDataSource = mockk<TvShowSimilarLocalDataSource>(relaxed = true)

        tvShowRepository = TvShowRepositoryImpl(
            tvShowDetailsRemoteDataSource,
            tvShowCastLocalDataSource,
            tvShowGalleryLocalDataSource,
            tvShowReviewLocalDataSource,
            tvShowLocalDataSource,
            tvShowSeasonLocalDataSource,
            tvShowSimilarLocalDataSource,
            networkConnectionChecker
        )
    }

    @Test
    fun `getTvShowDetails - should return tv show details when API delivers the goods`() = runTest {
        // Given
        val tvShowId = 550
        val language = "en"

        val expectedTvShow = mockTvShowDto.toLocalDto(language, tvShowId)

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
        } returns mockTvShowDto

        coEvery {
            tvShowLocalDataSource.getTvShowId(tvShowId, language)
        } returns mockTvShowDto.toLocalDto(language, tvShowId)

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
        val expectedTvShowCast = mockTvShowCreditsDto.cast?.map { it.toLocalDto(language, tvShowId) } ?: emptyList()

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        } returns mockTvShowCreditsDto

        coEvery {
            tvShowCastLocalDataSource.getCastByTvShowId(tvShowId, language)
        } returns expectedTvShowCast.map { it.toLocalDto(language, tvShowId) }

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
            val localDto =
                mockTvShowSimilarsDto.tvShowSimilarDto?.map { it.toLocalDto(language, page) }
                    ?: emptyList()

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getSimilarTvShows(
                    tvShowId,
                    page,
                    language
                )
            } returns mockTvShowSimilarsDto

            coEvery {
                tvShowSimilarLocalDataSource.getSimilarTvShows(
                    tvShowId,
                    page,
                    language
                )
            } returns localDto

            coEvery { tvShowSimilarLocalDataSource.addSimilarTvShows(any()) } returns Unit

            val result = tvShowRepository.getTvShowRecommendations(tvShowId, page)

            // Then
            assertEquals(expectedCast.first().title, result.first().title)
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

            val expectedCast = mockTvShowDto.productionCompanies ?: emptyList()

            // When
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowDetails(
                    tvShowId, language
                ).productionCompanies
            } returns mockTvShowDto.productionCompanies

            coEvery {
                tvShowLocalDataSource.getTvShowId(tvShowId, language)?.productionCompanies
            } returns mockTvShowDto.toLocalDto(language, tvShowId).productionCompanies

            coEvery {
                tvShowLocalDataSource.addTvShow(any())
            } returns Unit

            val result = tvShowRepository.getCompanyProducts(tvShowId)

            // Then
            assertEquals(expectedCast.first().name, result.first().name)
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
                mockTvShowReviewsDto.results?.map { it.toEntity().toLocalDto(language) }
                    ?: emptyList()
            coEvery {
                tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
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