package com.repository.media.repository

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.media.datasource.local.TvShowLocalDataSource
import com.repository.media.datasource.remote.TvShowDetailsRemoteDataSource
import com.repository.media.mapper.toEntity
import com.repository.media.mapper.toLocalDto
import com.repository.media.models.local.tvShow.TVShowGalleryEntity
import com.repository.media.models.remote.tvShow.TvShowSeasonDto
import com.repository.media.util.NetworkConnectionChecker
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TvShowRepositoryImplTest {
    private lateinit var tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource
    private lateinit var tvShowLocalDataSource: TvShowLocalDataSource
    private var networkConnectionChecker: NetworkConnectionChecker = mockk(relaxed = true)
    private lateinit var tvShowRepository: TvShowRepositoryImpl
    private var settingLocalDataSource: SettingLocalDataSource = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        tvShowDetailsRemoteDataSource = mockk<TvShowDetailsRemoteDataSource>(relaxed = true)
        tvShowLocalDataSource = mockk<TvShowLocalDataSource>(relaxed = true)

        tvShowRepository = TvShowRepositoryImpl(
            tvShowDetailsRemoteDataSource,
            tvShowLocalDataSource,
            networkConnectionChecker,
            settingLocalDataSource
        )
    }

    @Test
    fun `addRatingToTvShow - should throw FailedException when remote fails`() =
        runTest {
            // Given
            val movieId = 1
            val rating = 8.5f

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery {
                tvShowDetailsRemoteDataSource.addRatingToTvShow(movieId, rating)
            } throws RuntimeException("Failed")

            // When & Then
            assertThrows<FailedException> {
                tvShowRepository.addRatingToTvShow(movieId, rating)
            }
        }

    @Test
    fun `addRatingToTvShow - should throw NoInternetConnectionException when offline`() = runTest {
        // Given
        val movieId = 1
        val rating = 8.5f

        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        // When & Then
        assertThrows<NoInternetConnectionException> {
            tvShowRepository.addRatingToTvShow(movieId, rating)
        }
    }

    @Test
    fun `getTvShowDetails - should return tv show details when API delivers the goods`() = runTest {
        // Given
        val expectedTvShow = mockTvShowDto.toLocalDto(language, tvShowId)

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
        assertThat(result.title).isEqualTo(expectedTvShow.title)
    }

    @Test
    fun `getTvShowDetails - should not call remote when local data is available`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            assertThrows<FailedException> {
                tvShowRepository.getTvShowDetails(tvShowId)
            }
        }

    @Test
    fun `getTvShowCast - should return tv show cast when API delivers the goods`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        } returns mockTvShowCreditsDto

        coEvery {
            tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
        } returns expectedTvShowCast.map { it.toLocalDto(language, tvShowId) }

        coEvery {
            tvShowLocalDataSource.addTvShowCast(any())
        } returns Unit

        // When
        val result = tvShowRepository.getTvShowCast(tvShowId)

        // Then
        assertThat(result.first().name).isEqualTo(expectedTvShowCast.first().name)
    }

    @Test
    fun `getTvShowCast - should not call remote when local cast is available`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        } returns mockTvShowCreditsDto

        coEvery {
            tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
        } returns expectedTvShowCast.map { it.toLocalDto(language, tvShowId) }

        coEvery {
            tvShowLocalDataSource.addTvShowCast(any())
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

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        } returns mockTvShowCreditsDto

        coEvery {
            tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
        } returns expectedTvShowCast.map { it.toLocalDto(language, tvShowId) }

        coEvery {
            tvShowLocalDataSource.addTvShowCast(any())
        } returns Unit

        // When
        tvShowRepository.getTvShowCast(tvShowId)

        // Then
        coVerify(exactly = 1) {
            tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
        }
    }

    @Test
    fun `getTvShowCast - should not call addCast when cast already exists`() = runTest {
        // Given
        val expectedTvShowCast = mockTvShowCreditsDto.cast ?: emptyList()

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
        } returns mockTvShowCreditsDto

        coEvery {
            tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
        } returns expectedTvShowCast.map { it.toLocalDto(language, tvShowId) }

        coEvery {
            tvShowLocalDataSource.addTvShowCast(any())
        } returns Unit

        // When
        tvShowRepository.getTvShowCast(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowLocalDataSource.addTvShowCast(any())
        }
    }


    @Test
    fun `getTvShowCast - should fetch and save cast from remote when local data is empty`() =
        runTest {
            // Given
            val remoteCast = mockTvShowCreditsDto.cast ?: emptyList()

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowLocalDataSource.getCastByTvShowId(tvShowId, language)
            } returns emptyList() andThen
                    remoteCast.map { it.toLocalDto(language, tvShowId) }

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowCredits(tvShowId, language)
            } returns mockTvShowCreditsDto

            coEvery {
                tvShowLocalDataSource.addTvShowCast(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowCast(tvShowId)

            // Then
            assertThat(result.first().name).isEqualTo(remoteCast.first().name)
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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowDetailsRemoteDataSource.getSimilarTvShows(
                    tvShowId,
                    page,
                    language
                )
            } returns mockTvShowSimilarsDto

            coEvery {
                tvShowLocalDataSource.getSimilarTvShows(
                    tvShowId,
                    page,
                    language
                )
            } returns localDto

            coEvery { tvShowLocalDataSource.addSimilarTvShows(any()) } returns Unit

            val result = tvShowRepository.getTvShowRecommendations(tvShowId, page)

            // Then
            assertThat(result.first().title).isEqualTo(expectedCast.first().title)
        }

    @Test
    fun `getTvShowRecommendations - should call remote data source once`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
        } returns mockTvShowSimilarsDto

        coEvery {
            tvShowLocalDataSource.getSimilarTvShows(tvShowId, page, language)
        } returns emptyList()

        coEvery { tvShowLocalDataSource.addSimilarTvShows(any()) } returns Unit

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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
            } returns mockTvShowSimilarsDto

            coEvery {
                tvShowLocalDataSource.getSimilarTvShows(tvShowId, page, language)
            } returns emptyList()

            coEvery { tvShowLocalDataSource.addSimilarTvShows(any()) } returns Unit

            // When
            tvShowRepository.getTvShowRecommendations(tvShowId, page)

            // Then
            coVerify(exactly = 1) {
                tvShowLocalDataSource.addSimilarTvShows(any())
            }
        }


    @Test
    fun `getTvShowRecommendations - should fetch and save recommendations from remote when local data is empty`() =
        runTest {
            // Given
            val remoteRecommendations = mockTvShowSimilarsDto.tvShowSimilarDto ?: emptyList()

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowLocalDataSource.getSimilarTvShows(tvShowId, page, language)
            } returns emptyList() andThen
                    remoteRecommendations.map { it.toLocalDto(tvShowId, language, page) }

            coEvery {
                tvShowDetailsRemoteDataSource.getSimilarTvShows(tvShowId, page, language)
            } returns mockTvShowSimilarsDto

            coEvery {
                tvShowLocalDataSource.addSimilarTvShows(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowRecommendations(tvShowId, page)

            // Then
            assertThat(result.first().title).isEqualTo(remoteRecommendations.first().title)
        }

    @Test
    fun `getTvShowGallery - should return tv show gallery when API delivers the goods`() = runTest {
        // Given
        val expectedImages = mockTvShowLogoDto.toLocalDto(tvShowId).images

        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId)
        } returns mockTvShowLogoDto

        coEvery {
            tvShowLocalDataSource.getGalleryByTvShowId(tvShowId)
        } returns TVShowGalleryEntity(
            id = 0,
            tvShowId = 123,
            images = expectedImages.map { it },
        )

        coEvery {
            tvShowLocalDataSource.addTvShowGallery(any())
        } returns Unit

        // When
        val result = tvShowRepository.getTvShowGallery(tvShowId)

        // Then
        assertThat(result).isEqualTo(expectedImages.map { it.toEntity() })
    }

    @Test
    fun `getTvShowGallery - should not call remote data source when local gallery is available`() =
        runTest {
            // Given
            coEvery { tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId) } returns mockTvShowLogoDto
            coEvery { tvShowLocalDataSource.getGalleryByTvShowId(tvShowId) } returns mockTvShowLogoDto.toLocalDto(
                tvShowId
            )
            coEvery { tvShowLocalDataSource.addTvShowGallery(any()) } returns Unit

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
        coEvery { tvShowLocalDataSource.getGalleryByTvShowId(tvShowId) } returns mockTvShowLogoDto.toLocalDto(
            tvShowId
        )
        coEvery { tvShowLocalDataSource.addTvShowGallery(any()) } returns Unit

        // When
        tvShowRepository.getTvShowGallery(tvShowId)

        // Then
        coVerify(exactly = 1) {
            tvShowLocalDataSource.getGalleryByTvShowId(tvShowId)
        }
    }

    @Test
    fun `getTvShowGallery - should not save gallery locally when it already exists`() = runTest {
        // Given
        coEvery { tvShowDetailsRemoteDataSource.getTvShowImages(tvShowId) } returns mockTvShowLogoDto
        coEvery { tvShowLocalDataSource.getGalleryByTvShowId(tvShowId) } returns mockTvShowLogoDto.toLocalDto(
            tvShowId
        )
        coEvery { tvShowLocalDataSource.addTvShowGallery(any()) } returns Unit

        // When
        tvShowRepository.getTvShowGallery(tvShowId)

        // Then
        coVerify(exactly = 0) {
            tvShowLocalDataSource.addTvShowGallery(any())
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
                tvShowLocalDataSource.getGalleryByTvShowId(tvShowId)
            } returns null

            coEvery {
                tvShowLocalDataSource.addTvShowGallery(any())
            } just Runs

            // When & Then
            assertThrows<FailedException> {
                tvShowRepository.getTvShowGallery(tvShowId)
            }
        }

    @Test
    fun `getCompanyProducts - should return company products when API delivers the goods`() =
        runTest {
            // Given
            val expectedCast = mockTvShowDto.productionCompanies ?: emptyList()

            // When
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            assertThat(result.first().name).isEqualTo(expectedCast.first().name)
        }

    @Test
    fun `getCompanyProducts - should not call remote data source when local movie is available`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            assertThat(result.first().name).isEqualTo(remoteProductionCompanies.first().name)

        }

    @Test
    fun `getTvShowReview - should return tv show review when API delivers the goods`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowReviews(
                    tvShowId, page, language
                )
            } returns mockTvShowReviewsDto

            val dto =
                mockTvShowReviewsDto.results ?: emptyList()
            coEvery {
                tvShowLocalDataSource.getReviewsByTvShowId(tvShowId, language)
            } returns dto.map { it.toLocalDto(tvShowId, language) }

            coEvery {
                tvShowLocalDataSource.addTvShowReviews(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowReview(tvShowId, page)

            // Then
            val expectedReviews =
                mockTvShowReviewsDto.results?.map { it.toLocalDto(tvShowId, language).toEntity() }
                    ?: emptyList()
            assertThat(result.first().createdAt).isEqualTo(expectedReviews.first().createdAt)
        }

    @Test
    fun `getTvShowReview - should call remote data source`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
        } returns mockTvShowReviewsDto

        coEvery {
            tvShowLocalDataSource.getReviewsByTvShowId(tvShowId, language)
        } returns emptyList()

        coEvery { tvShowLocalDataSource.addTvShowReviews(any()) } just Runs

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
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
        } returns mockTvShowReviewsDto

        val dtoList =
            mockTvShowReviewsDto.results?.map { it.toLocalDto(tvShowId, language) } ?: emptyList()

        coEvery {
            tvShowLocalDataSource.getReviewsByTvShowId(tvShowId, language)
        } returns dtoList

        coEvery { tvShowLocalDataSource.addTvShowReviews(any()) } just Runs

        // When
        tvShowRepository.getTvShowReview(tvShowId, page)

        // Then
        coVerify(exactly = 0) {
            tvShowLocalDataSource.addTvShowReviews(any())
        }
    }

    @Test
    fun `getTvShowReview - should fetch and save reviews from remote when local data is empty`() =
        runTest {
            // Given
            val remoteReviews = mockTvShowReviewsDto.results ?: emptyList()

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowLocalDataSource.getReviewsByTvShowId(tvShowId, language)
            } returns emptyList() andThen remoteReviews.map { it.toLocalDto(tvShowId, language) }

            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowReviews(tvShowId, page, language)
            } returns mockTvShowReviewsDto

            coEvery {
                tvShowLocalDataSource.addTvShowReviews(any())
            } just Runs

            // When
            val result = tvShowRepository.getTvShowReview(tvShowId, page)

            // Then
            assertThat(result).isEqualTo(remoteReviews.map {
                it.toLocalDto(tvShowId, language).toEntity()
            })
        }

    @Test
    fun `getSeasonDetails - should return season details when API delivers the goods`() =
        runTest {
            // Given
            val mockTvShowSeasonDto = TvShowSeasonDto(
                name = "stronger things"
            )

            val expectedSeason = mockTvShowSeasonDto.toLocalDto(tvShowId).toEntity()

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowDetailsRemoteDataSource.getSeasonDetails(
                    tvShowId, seasonNumber, language
                )
            } returns mockTvShowSeasonDto

            coEvery {
                tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
            } returns mockTvShowSeasonDto.toLocalDto(tvShowId)

            coEvery {
                tvShowLocalDataSource.addTvShowSeason(any())
            } returns Unit

            // When
            val result = tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

            // Then
            assertThat(result.name).isEqualTo(expectedSeason.name)
        }

    @Test
    fun `getSeasonDetails -  should not call remote data source when season exists locally`() =
        runTest {
            // Given
            val mockTvShowSeasonDto = TvShowSeasonDto(name = "stronger things")

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
            } returns mockTvShowSeasonDto

            coEvery {
                tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
            } returns mockTvShowSeasonDto.toLocalDto(
                tvShowId
            )

            coEvery { tvShowLocalDataSource.addTvShowSeason(any()) } returns Unit

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

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
        } returns mockTvShowSeasonDto

        coEvery {
            tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                tvShowId,
                seasonNumber
            )
        } returns mockTvShowSeasonDto.toLocalDto(tvShowId)

        coEvery { tvShowLocalDataSource.addTvShowSeason(any()) } returns Unit

        // When
        tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 1) {
            tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                tvShowId,
                seasonNumber
            )
        }
    }

    @Test
    fun `getSeasonDetails - should throw FailedException when local data source returns null after adding`() =
        runTest {
            // Given
            val mockTvShowSeasonDto = TvShowSeasonDto(
                name = "stronger things"
            )

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
            } returns mockTvShowSeasonDto

            coEvery {
                tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                    tvShowId,
                    seasonNumber
                )
            } returns null

            coEvery {
                tvShowLocalDataSource.addTvShowSeason(any())
            } just Runs

            // When & Then
            assertThrows<FailedException> {
                tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)
            }
        }

    @Test
    fun `getSeasonDetails - should add season details to local data source`() = runTest {
        // Given
        val mockTvShowSeasonDto = TvShowSeasonDto(name = "stronger things")

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowDetailsRemoteDataSource.getSeasonDetails(tvShowId, seasonNumber, language)
        } returns mockTvShowSeasonDto

        coEvery {
            tvShowLocalDataSource.getSeasonByTvShowIdAndSeasonNumber(
                tvShowId,
                seasonNumber
            )
        } returns mockTvShowSeasonDto.toLocalDto(tvShowId)

        coEvery { tvShowLocalDataSource.addTvShowSeason(any()) } returns Unit

        // When
        tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)

        // Then
        coVerify(exactly = 0) {
            tvShowLocalDataSource.addTvShowSeason(any())
        }
    }

    @Test
    fun `getTvShowCast should throw FailedException when remote throws generic exception`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                tvShowLocalDataSource.getCastByTvShowId(
                    tvShowId,
                    language
                )
            } returns emptyList()
            coEvery {
                tvShowDetailsRemoteDataSource.getTvShowCredits(
                    tvShowId,
                    language
                )
            } throws RuntimeException("Something went wrong")

            // When & Then
            assertThrows<FailedException> {
                tvShowRepository.getTvShowCast(tvShowId)
            }
        }

    @Test
    fun `getTvShowRecommendations - should throw NoInternetConnectionException when offline`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getTvShowRecommendations(tvShowId, page)
            }
        }

    @Test
    fun `getCompanyProducts - should return empty list when productionCompanies is null`() =
        runTest {
            // Given
            val local =
                mockTvShowDto.toLocalDto(language, tvShowId).copy(productionCompanies = emptyList())

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            tvShowLocalDataSource.getReviewsByTvShowId(
                tvShowId,
                language
            )
        } returns emptyList()
        coEvery {
            tvShowDetailsRemoteDataSource.getTvShowReviews(
                tvShowId,
                page,
                language
            )
        } returns emptyDto

        // When
        val result = tvShowRepository.getTvShowReview(tvShowId, page)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getTvShowDetails - should throw NoInternetConnectionException when there is no internet`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
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
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                tvShowRepository.getSeasonDetails(tvShowId, seasonNumber)
            }
        }

    @Test
    fun `deleteTvShowRating should return FailedException when remote throws exception`() =
        runTest {
            // Given
            val causeException = RuntimeException("Network error")
            coEvery { tvShowDetailsRemoteDataSource.deleteTvShowRating(tvShowId = 123) } throws causeException

            // When
            val result = runCatching {
                tvShowRepository.deleteTvShowRating(123)
            }

            // Then
            Truth.assertThat(result.exceptionOrNull())
                .isInstanceOf(FailedException::class.java)
            coVerify(exactly = 1) { tvShowDetailsRemoteDataSource.deleteTvShowRating(tvShowId = 123) }
        }

    @Test
    fun `deleteTvShowRating should succeed when remote call succeeds`() = runTest {
        // Given
        coEvery { tvShowDetailsRemoteDataSource.deleteTvShowRating(tvShowId = 123) } coAnswers { true }

        // When
        val result = runCatching {
            tvShowRepository.deleteTvShowRating(123)
        }

        // Then
        Truth.assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { tvShowDetailsRemoteDataSource.deleteTvShowRating(tvShowId = 123) }
    }

    @Test
    fun `deleteTvShowRating should throw NoInternetConnectionException when offline`() = runTest {
        // Given
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        // When & Then
        assertThrows<NoInternetConnectionException> {
            tvShowRepository.deleteTvShowRating(123)
        }
        coVerify(exactly = 0) { tvShowDetailsRemoteDataSource.deleteTvShowRating(any()) }
    }

    private companion object {
        val tvShowId = 123
        val language = "en"

        val seasonNumber = 1

        val page = 1
    }
}