package com.repository.tvshow.repository

import com.repository.dataSource.local.TvShowCastLocalDataSource
import com.repository.dataSource.local.TvShowGalleryLocalDataSource
import com.repository.dataSource.local.TvShowLocalDataSource
import com.repository.dataSource.local.TvShowReviewLocalDataSource
import com.repository.dataSource.local.TvShowSeasonLocalDataSource
import com.repository.dataSource.local.TvShowSimilarLocalDataSource
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.mapper.toEntity
import com.repository.mapper.toLocalDto
import com.repository.model.local.GalleryEntity
import com.repository.model.remote.TvShowSeasonDto
import com.repository.repository.TvShowRepositoryImpl
import com.repository.tvshow.testUtils.mockTvShowCreditsDto
import com.repository.tvshow.testUtils.mockTvShowDto
import com.repository.tvshow.testUtils.mockTvShowLogoDto
import com.repository.tvshow.testUtils.mockTvShowReviewsDto
import com.repository.tvshow.testUtils.mockTvShowSimilarsDto
import com.repository.util.NetworkConnectionChecker
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
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
    fun `getTvShowDetails - should not call remote when local data is available`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(
                tvShowId,
                language
            )
        } returns mockTvShowDto
        coEvery {
            tvShowLocalDataSource.getTvShowId(
                tvShowId,
                language
            )
        } returns mockTvShowDto.toLocalDto(language, tvShowId)
        coEvery { tvShowLocalDataSource.addTvShow(any()) } returns Unit

        // When
        tvShowRepository.getTvShowDetails(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
        }
    }

    @Test
    fun `getTvShowDetails - should call getTvShowId from local data source once`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(
                tvShowId,
                language
            )
        } returns mockTvShowDto
        coEvery {
            tvShowLocalDataSource.getTvShowId(
                tvShowId,
                language
            )
        } returns mockTvShowDto.toLocalDto(language, tvShowId)
        coEvery { tvShowLocalDataSource.addTvShow(any()) } returns Unit

        // When
        tvShowRepository.getTvShowDetails(tvShowId)

        // Then
        coVerify(exactly = 1) {
            tvShowLocalDataSource.getTvShowId(tvShowId, language)
        }
    }

    @Test
    fun `getTvShowDetails - should not insert Tv show when local data is available`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(
                tvShowId,
                language
            )
        } returns mockTvShowDto
        coEvery {
            tvShowLocalDataSource.getTvShowId(
                tvShowId,
                language
            )
        } returns mockTvShowDto.toLocalDto(language, tvShowId)
        coEvery { tvShowLocalDataSource.addTvShow(any()) } returns Unit

        // When
        tvShowRepository.getTvShowDetails(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowLocalDataSource.addTvShow(any())
        }
    }

    @Test
    fun `getTvShowCast - should return tv show cast when API delivers the goods`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

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
        assertEquals(expectedTvShowCast.first().name, result.first().name)
    }

    @Test
    fun `getTvShowCast - should not call remote when local cast is available`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

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
        tvShowRepository.getTvShowCast(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        }
    }

    @Test
    fun `getTvShowCast - should call local getCastByTvShowId once`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

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
        tvShowRepository.getTvShowCast(tvShowId)

        // Then
        coVerify(exactly = 1) {
            tvShowCastLocalDataSource.getCastByTvShowId(tvShowId, language)
        }
    }

    @Test
    fun `getTvShowCast - should not call addCast when cast already exists`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

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
        tvShowRepository.getTvShowCast(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowCastLocalDataSource.addCast(any())
        }
    }


    @Test
    fun `getTvShowRecommendations - should return tv show recommendations when API delivers the goods`() =
        runTest {
            // Given
            val expectedCast =
                mockTvShowSimilarsDto.tvShowSimilarDto?.map {
                    it.toLocalDto(
                        tvShowId,
                        language,
                        page
                    )
                } ?: emptyList()
            val localDto =
                mockTvShowSimilarsDto.tvShowSimilarDto?.map {
                    it.toLocalDto(
                        tvShowId,
                        language,
                        page
                    )
                }
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
    fun `getTvShowRecommendations - should call remote data source once`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
        } returns mockTvShowSimilarsDto

        coEvery {
            tvShowSimilarLocalDataSource.getSimilarTvShows(tvShowId, page, language)
        } returns emptyList()

        coEvery { tvShowSimilarLocalDataSource.addSimilarTvShows(any()) } returns Unit

        // When
        tvShowRepository.getTvShowRecommendations(tvShowId, page)

        // Then
        coVerify(exactly = 1) {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
        }
    }

    @Test
    fun `getTvShowRecommendations - should save fetched remote data to local database exactly once`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
        } returns mockTvShowSimilarsDto

        coEvery {
            tvShowSimilarLocalDataSource.getSimilarTvShows(tvShowId, page, language)
        } returns emptyList()

        coEvery { tvShowSimilarLocalDataSource.addSimilarTvShows(any()) } returns Unit

        // When
        tvShowRepository.getTvShowRecommendations(tvShowId, page)

        // Then
        coVerify(exactly = 1) {
            tvShowSimilarLocalDataSource.addSimilarTvShows(any())
        }
    }


    @Test
    fun `getTvShowGallery - should return tv show gallery when API delivers the goods`() = runTest {
        // Given
        val expectedImages = mockTvShowLogoDto.toLocalDto(tvShowId).images

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
        } returns mockTvShowLogoDto

        coEvery {
            tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)
        } returns GalleryEntity(
            id = 0,
            tvShowId = 123,
            images = expectedImages.map { it },
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
    fun `getTvShowGallery - should not call remote data source when local gallery is available`() = runTest {
        // Given
        coEvery { tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId) } returns mockTvShowLogoDto
        coEvery { tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId) } returns mockTvShowLogoDto.toLocalDto(
            tvShowId
        )
        coEvery { tvShowGalleryLocalDataSource.addGallery(any()) } returns Unit

        // When
        tvShowRepository.getTvShowGallery(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
        }
    }

    @Test
    fun `getTvShowGallery - should get gallery from local data source`() = runTest {
        // Given
        coEvery { tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId) } returns mockTvShowLogoDto
        coEvery { tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId) } returns mockTvShowLogoDto.toLocalDto(
            tvShowId
        )
        coEvery { tvShowGalleryLocalDataSource.addGallery(any()) } returns Unit

        // When
        tvShowRepository.getTvShowGallery(tvShowId)

        // Then
        coVerify(exactly = 1) {
            tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)
        }
    }

    @Test
    fun `getTvShowGallery - should not save gallery locally when it already exists`() = runTest {
        // Given
        coEvery { tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId) } returns mockTvShowLogoDto
        coEvery { tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId) } returns mockTvShowLogoDto.toLocalDto(
            tvShowId
        )
        coEvery { tvShowGalleryLocalDataSource.addGallery(any()) } returns Unit

        // When
        tvShowRepository.getTvShowGallery(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowGalleryLocalDataSource.addGallery(any())
        }
    }


    @Test
    fun `getCompanyProducts - should return company products when API delivers the goods`() =
        runTest {
            // Given
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
    fun `getCompanyProducts - should not call remote data source when local movie is available`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language).productionCompanies
        } returns mockTvShowDto.productionCompanies

        coEvery {
            tvShowLocalDataSource.getTvShowId(tvShowId, language)?.productionCompanies
        } returns mockTvShowDto.toLocalDto(language, tvShowId).productionCompanies

        coEvery { tvShowLocalDataSource.addTvShow(any()) } returns Unit

        // When
        tvShowRepository.getCompanyProducts(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
        }
    }

    @Test
    fun `getCompanyProducts - should insert TV show into local data source`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language).productionCompanies
        } returns mockTvShowDto.productionCompanies

        coEvery {
            tvShowLocalDataSource.getTvShowId(tvShowId, language)?.productionCompanies
        } returns mockTvShowDto.toLocalDto(language, tvShowId).productionCompanies

        coEvery { tvShowLocalDataSource.addTvShow(any()) } returns Unit

        // When
        tvShowRepository.getCompanyProducts(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowLocalDataSource.addTvShow(any())
        }
    }

    @Test
    fun `getCompanyProducts - should get production companies from local data source`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language).productionCompanies
        } returns mockTvShowDto.productionCompanies

        coEvery {
            tvShowLocalDataSource.getTvShowId(tvShowId, language)?.productionCompanies
        } returns mockTvShowDto.toLocalDto(language, tvShowId).productionCompanies

        coEvery { tvShowLocalDataSource.addTvShow(any()) } returns Unit

        // When
        tvShowRepository.getCompanyProducts(tvShowId)

        // Then
        coVerify(exactly = 1) {
            tvShowLocalDataSource.getTvShowId(tvShowId, language)
        }
    }

    @Test
    fun `getTvShowReview - should return tv show review when API delivers the goods`() =
        runTest {
            // Given
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowReviews(
                    tvShowId, page, language
                )
            } returns mockTvShowReviewsDto

            val dto =
                mockTvShowReviewsDto.results ?: emptyList()
            coEvery {
                tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
            } returns dto.map { it.toLocalDto(tvShowId, language) }

            coEvery {
                tvShowReviewLocalDataSource.addReview(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowReview(tvShowId, page)

            // Then
            val expectedReviews =
                mockTvShowReviewsDto.results?.map { it.toLocalDto(tvShowId, language).toEntity() }
                    ?: emptyList()
            assertEquals(expectedReviews.first().createdAt, result.first().createdAt)
        }

    @Test
    fun `getTvShowReview - should call remote data source`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
        } returns mockTvShowReviewsDto

        coEvery {
            tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
        } returns emptyList()

        coEvery { tvShowReviewLocalDataSource.addReview(any()) } just Runs

        // When
        tvShowRepository.getTvShowReview(tvShowId, page)

        // Then
        coVerify(exactly = 1) {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
        }
    }


    @Test
    fun `getTvShowReview - should not add review when local reviews are available`() = runTest {
        // Given
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
        } returns mockTvShowReviewsDto

        val dtoList =
            mockTvShowReviewsDto.results?.map { it.toLocalDto(tvShowId, language) } ?: emptyList()

        coEvery {
            tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
        } returns dtoList

        coEvery { tvShowReviewLocalDataSource.addReview(any()) } just Runs

        // When
        tvShowRepository.getTvShowReview(tvShowId, page)

        // Then
        coVerify(exactly = 0) {
            tvShowReviewLocalDataSource.addReview(any())
        }
    }

    @Test
    fun `getSeasonDetails - should return season details when API delivers the goods`() =
        runTest {
            // Given
            val mockTvShowSeasonDto = TvShowSeasonDto(
                name = "stronger things"
            )

            val expectedSeason = mockTvShowSeasonDto.toLocalDto(tvShowId).toEntity()

            coEvery {
                tvShowDetailsRemoteDataSource.getSeasonDetails(
                    tvShowId, seasonNumber, language
                )
            } returns mockTvShowSeasonDto

            coEvery {
                tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
            } returns mockTvShowSeasonDto.toLocalDto(tvShowId)

            coEvery {
                tvShowSeasonLocalDataSource.addSeasonDetails(any())
            } returns Unit

            // When
            val result = tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

            // Then
            assertEquals(expectedSeason.name, result.name)
        }

    @Test
    fun `getSeasonDetails -  should not call remote data source when season exists locally`() = runTest {
        // Given
        val mockTvShowSeasonDto = TvShowSeasonDto(name = "stronger things")

        coEvery {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
        } returns mockTvShowSeasonDto

        coEvery { tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId) } returns mockTvShowSeasonDto.toLocalDto(
            tvShowId
        )

        coEvery { tvShowSeasonLocalDataSource.addSeasonDetails(any()) } returns Unit

        // When
        tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 0) {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
        }
    }

    @Test
    fun `getSeasonDetails - should call local getSeasonDetailsByTvShowId`() = runTest {
        // Given
        val mockTvShowSeasonDto = TvShowSeasonDto(name = "stronger things")

        coEvery {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
        } returns mockTvShowSeasonDto

        coEvery {
            tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
        } returns mockTvShowSeasonDto.toLocalDto(tvShowId)

        coEvery { tvShowSeasonLocalDataSource.addSeasonDetails(any()) } returns Unit

        // When
        tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 1) {
            tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
        }
    }

    @Test
    fun `getSeasonDetails - should add season details to local data source`() = runTest {
        // Given
        val mockTvShowSeasonDto = TvShowSeasonDto(name = "stronger things")

        coEvery {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
        } returns mockTvShowSeasonDto

        coEvery {
            tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
        } returns mockTvShowSeasonDto.toLocalDto(tvShowId)

        coEvery { tvShowSeasonLocalDataSource.addSeasonDetails(any()) } returns Unit

        // When
        tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 0) {
            tvShowSeasonLocalDataSource.addSeasonDetails(any())
        }
    }

    private companion object {
        val tvShowId = 123
        val language = "en"

        val page = 1
        val seasonNumber = 1
    }
}