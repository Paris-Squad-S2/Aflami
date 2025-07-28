package com.repository.tvshow.repository

import com.domain.mediaDetails.exception.NoCastFoundException
import com.domain.mediaDetails.exception.NoGalleryFoundException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.exception.NoSeasonFoundException
import com.domain.mediaDetails.exception.NoTvShowFoundException
import com.google.common.truth.Truth.assertThat
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
import com.repository.tvshow.testUtils.mockTvShowVideosDto
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
import org.junit.jupiter.api.assertThrows
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
    fun `getTvShowDetails - should throw NoFoundTvShowException when local data source returns null after adding`() =
        runTest {
            // Given
            val tvShowId = 550
            val language = "en"

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
            } returns mockTvShowDto

            coEvery {
                tvShowLocalDataSource.getTvShowId(tvShowId, language)
            } returns null

            coEvery {
                tvShowLocalDataSource.addTvShow(any())
            } just Runs

            // When & Then
            assertThrows<NoTvShowFoundException> {
                tvShowRepository.getTvShowDetails(tvShowId)
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
    fun `getTvShowCast - should fetch and save cast from remote when local data is empty`() =
        runTest {
            // Given
            val remoteCast = mockTvShowCreditsDto.cast ?: emptyList()

            coEvery {
                tvShowCastLocalDataSource.getCastByTvShowId(tvShowId, language)
            } returns emptyList() andThen
                    remoteCast.map { it.toLocalDto(language, tvShowId) }

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
            } returns mockTvShowCreditsDto

            coEvery {
                tvShowCastLocalDataSource.addCast(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowCast(tvShowId)

            // Then
            assertEquals(remoteCast.first().name, result.first().name)
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
    fun `getTvShowRecommendations - should save fetched remote data to local database exactly once`() =
        runTest {
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
    fun `getTvShowRecommendations - should fetch and save recommendations from remote when local data is empty`() =
        runTest {
            // Given
            val remoteRecommendations = mockTvShowSimilarsDto.tvShowSimilarDto ?: emptyList()

            coEvery {
                tvShowSimilarLocalDataSource.getSimilarTvShows(tvShowId, page, language)
            } returns emptyList() andThen
                    remoteRecommendations.map { it.toLocalDto(tvShowId, language, page) }

            coEvery {
                tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
            } returns mockTvShowSimilarsDto

            coEvery {
                tvShowSimilarLocalDataSource.addSimilarTvShows(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowRecommendations(tvShowId, page)

            // Then
            assertEquals(remoteRecommendations.first().title, result.first().title)
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
    fun `getTvShowGallery - should not call remote data source when local gallery is available`() =
        runTest {
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
    fun `getTvShowGallery - should throw NoFundGalleryTvShowException when local data source returns null after adding`() =
        runTest {
            // Given
            val tvShowId = 123

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
            } returns mockTvShowLogoDto

            coEvery {
                tvShowGalleryLocalDataSource.getGalleryByTvShowId(tvShowId)
            } returns null

            coEvery {
                tvShowGalleryLocalDataSource.addGallery(any())
            } just Runs

            // When & Then
            assertThrows<NoGalleryFoundException> {
                tvShowRepository.getTvShowGallery(tvShowId)
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
    fun `getCompanyProducts - should not call remote data source when local movie is available`() =
        runTest {
            // Given
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowDetails(
                    tvShowId,
                    language
                ).productionCompanies
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
    fun `getCompanyProducts - should fetch and save company products from remote when local data is empty`() =
        runTest {
            // Given
            val remoteProductionCompanies = mockTvShowDto.productionCompanies ?: emptyList()

            coEvery {
                tvShowLocalDataSource.getTvShowId(tvShowId, language)?.productionCompanies
            } returns emptyList() andThen mockTvShowDto.toLocalDto(
                language,
                tvShowId
            ).productionCompanies
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowDetails(tvShowId, language)
            } returns mockTvShowDto

            coEvery {
                tvShowLocalDataSource.addTvShow(any())
            } just Runs

            // When
            val result = tvShowRepository.getCompanyProducts(tvShowId)

            // Then
            assertEquals(remoteProductionCompanies.first().name, result.first().name)

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
    fun `getTvShowReview - should fetch and save reviews from remote when local data is empty`() =
        runTest {
            // Given
            val remoteReviews = mockTvShowReviewsDto.results ?: emptyList()

            coEvery {
                tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language)
            } returns emptyList() andThen remoteReviews.map { it.toLocalDto(tvShowId, language) }

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
            } returns mockTvShowReviewsDto

            coEvery {
                tvShowReviewLocalDataSource.addReview(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowReview(tvShowId, page)

            // Then
            assertEquals(result, remoteReviews.map { it.toLocalDto(tvShowId, language).toEntity() })
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
    fun `getSeasonDetails -  should not call remote data source when season exists locally`() =
        runTest {
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
        @Test
        fun `getSeasonDetails - should throw NoSeasonFoundException when local data source returns null after adding`() =
            runTest {
                // Given
                val mockTvShowSeasonDto = TvShowSeasonDto(
                    name = "stronger things"
                )

                coEvery {
                    tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
                } returns mockTvShowSeasonDto

                coEvery {
                    tvShowSeasonLocalDataSource.getSeasonDetailsByTvShowId(tvShowId)
                } returns null

                coEvery {
                    tvShowSeasonLocalDataSource.addSeasonDetails(any())
                } just Runs

                // When & Then
                assertThrows<NoSeasonFoundException> {
                    tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)
                }
            }


        @Test
        fun `getTrailerVideoForTvShow - should return trailers from remote when network is available`() =
            runTest {
                // Given
                val expectedTrailers =
                    mockTvShowVideosDto.tvShowVideoResultDto?.map { it.toEntity() } ?: emptyList()

                coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
                coEvery { tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId) } returns mockTvShowVideosDto

                // When
                val result = tvShowRepository.getTrailerVideoForTvShow(tvShowId)

                // Then
                assertThat(result).isEqualTo(expectedTrailers)

            }

        @Test
        fun `getTrailerVideoForTvShow - should call remote data source when network is available`() =
            runTest {
                // Given
                coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
                coEvery { tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId) } returns mockTvShowVideosDto

                // When
                tvShowRepository.getTrailerVideoForTvShow(tvShowId)

                // Then
                coVerify(exactly = 1) {
                    tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId)
                }
            }


        @Test
        fun `getTrailerVideoForTvShow - should return empty list when remote returns no trailers`() =
            runTest {
                // Given
                val emptyVideosDto = mockTvShowVideosDto.copy(tvShowVideoResultDto = null)

                coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
                coEvery { tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId) } returns emptyVideosDto

                // When
                val result = tvShowRepository.getTrailerVideoForTvShow(tvShowId)

                // Then
                assertThat(result).isEmpty()
            }

        @Test
        fun `getTrailerVideoForTvShow - should call remote data source even if no trailers exist`() =
            runTest {
                // Given
                val emptyVideosDto = mockTvShowVideosDto.copy(tvShowVideoResultDto = null)

                coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
                coEvery { tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId) } returns emptyVideosDto

                // When
                tvShowRepository.getTrailerVideoForTvShow(tvShowId)

                // Then
                coVerify(exactly = 1) {
                    tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(tvShowId)
                }
            }


        @Test
        fun `getTrailerVideoForTvShow - should throw NoInternetConnectionException when network is unavailable`() =
            runTest {
                // Given
                coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

                // When & Then
                assertThrows<NoInternetConnectionException> {
                    tvShowRepository.getTrailerVideoForTvShow(tvShowId)
                }
                coVerify(exactly = 0) { tvShowDetailsRemoteDataSource.getTrailerVideoForTvShow(any()) }
            }

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
    @Test
    fun `getTvShowCast should throw NoCastFoundException when remote throws generic exception`() = runTest {
        // Given
        coEvery { tvShowCastLocalDataSource.getCastByTvShowId(tvShowId, language) } returns emptyList()
        coEvery { tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language) } throws RuntimeException("Something went wrong")

        // When & Then
        assertThrows<NoCastFoundException> {
            tvShowRepository.getTvShowCast(tvShowId)
        }
    }
    @Test
    fun `getTvShowRecommendations - should throw NoInternetConnectionException when offline`() = runTest {
        // Given
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        // When & Then
        assertThrows<NoInternetConnectionException> {
            tvShowRepository.getTvShowRecommendations(tvShowId, page)
        }
    }
    @Test
    fun `getCompanyProducts - should return empty list when productionCompanies is null`() = runTest {
        // Given
        val local = mockTvShowDto.toLocalDto(language, tvShowId).copy(productionCompanies = emptyList())

        coEvery { tvShowLocalDataSource.getTvShowId(tvShowId, language) } returns local

        // When
        val result = tvShowRepository.getCompanyProducts(tvShowId)

        // Then
        assertThat(result).isEmpty()
    }
    @Test
    fun `getTvShowReview - should return empty list when remote returns null reviews`() = runTest {
        // Given
        val emptyDto = mockTvShowReviewsDto.copy(results = null)

        coEvery { tvShowReviewLocalDataSource.getReviewsByTvShowId(tvShowId, language) } returns emptyList()
        coEvery { tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language) } returns emptyDto

        // When
        val result = tvShowRepository.getTvShowReview(tvShowId, page)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getTvShowDetails - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getTvShowDetails(tvShowId)
            }
    }

    @Test
    fun `getTvShowCast - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getTvShowCast(tvShowId)
            }
        }

    @Test
    fun `getTvShowRecommendations - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getTvShowRecommendations(tvShowId, page)
            }
        }

    @Test
    fun `getTvShowGallery - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getTvShowGallery(tvShowId)
            }
        }

    @Test
    fun `getCompanyProducts - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getCompanyProducts(tvShowId)
            }
        }

    @Test
    fun `getTvShowReview - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getTvShowReview(tvShowId, page)
            }
        }

    @Test
    fun `getSeasonDetails - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)
            }
        }



    private companion object {
        val tvShowId = 123
        val language = "en"

        val seasonNumber = 1

        val page = 1
    }
}