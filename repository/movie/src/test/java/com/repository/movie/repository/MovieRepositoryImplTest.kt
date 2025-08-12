package com.repository.movie.repository

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.MovieSimilar
import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.exception.FailedException
import com.paris_2.domain.media.exception.NoInternetConnectionException
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.remote.MovieRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.mapper.toLocalDto
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieProductionCompanyDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.testUtils.mockMovieCreditsDto
import com.repository.movie.testUtils.mockMovieDto
import com.repository.movie.testUtils.mockMovieImagesDto
import com.repository.movie.testUtils.mockMovieSimilarsDto
import com.repository.movie.testUtils.mockMovieVideosDto
import com.repository.movie.testUtils.review
import com.repository.movie.testUtils.reviewRemoteDto
import com.repository.movie.util.NetworkConnectionChecker
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MovieRepositoryImplTest {
    private lateinit var movieRepository: MovieRepositoryImpl
    private var movieRemoteDataSource: MovieRemoteDataSource = mockk(relaxed = true)
    private var movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
    private var networkConnectionChecker: NetworkConnectionChecker = mockk(relaxed = true)
    private var settingLocalDataSource: SettingLocalDataSource = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)

        movieRepository = MovieRepositoryImpl(
            networkConnectionChecker,
            movieLocalDataSource,
            movieRemoteDataSource,
            settingLocalDataSource
        )
    }

    @Test
    fun `getMovieCast should throw NoInternetConnectionException when network is unavailable`() =
        runTest {
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)
            val result = runCatching { movieRepository.getMovieCast(1) }
            assertThat(result.exceptionOrNull()).isInstanceOf(NoInternetConnectionException::class.java)
        }

    @Test
    fun `getMovieCast should throw NoInternetConnectionException when network is unavailable - alternative`() =
        runTest {
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)
            val result = runCatching { movieRepository.getMovieCast(1) }
            assertThat(result.exceptionOrNull()).isInstanceOf(NoInternetConnectionException::class.java)
        }

    @Test
    fun `getMovieCast should return empty list when local and remote return empty`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                1,
                "en"
            )
        } returns emptyList<CastEntity>()
        coEvery { movieRemoteDataSource.getMovieCredits(1, "en") } returns MovieCreditsDto()
        val result = movieRepository.getMovieCast(1)
        assertThat(result).isEmpty()
    }

    @Test
    fun `getCompanyProducts should return empty list when both local and remote return empty`() =
        runTest {
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieRemoteDataSource.getMovieDetails(
                    movieId,
                    language
                )
            } returns mockMovieDto.copy(productionCompanies = null)
            val result = movieRepository.getCompanyProducts(movieId)
            assertThat(result).isEmpty()
        }

    @Test
    fun `getMovieReview should return empty list when local is null and remote returns empty`() =
        runTest {
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")

            coEvery {
                movieLocalDataSource.getReviewsByMovieId(
                    movieId,
                    language
                )
            } returns null
            coEvery {
                movieRemoteDataSource.getMovieReviews(
                    movieId,
                    page,
                    language
                )
            } returns MovieReviewsDto(results = emptyList())
            val result = movieRepository.getMovieReview(movieId, page)
            assertThat(result).isEmpty()
        }

    @Test
    fun `getTrailerVideoForMovie should return empty list when remote returns null`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")

        coEvery { movieRemoteDataSource.getTrailerVideoForMovie(movieId) } returns mockMovieVideosDto.copy(
            movieVideoResultDto = null
        )
        val result = movieRepository.getTrailerVideoForMovie(movieId)
        assertThat(result).isEmpty()
    }

    @Test
    fun `getMovieDetails should throw FailedException when remote fails`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieRemoteDataSource.getMovieDetails(
                movieId,
                "en"
            )
        } throws FailedException("")

        val result = runCatching { movieRepository.getMovieDetails(movieId) }

        assertThat(result.exceptionOrNull()).isInstanceOf(FailedException::class.java)
    }

    @Test
    fun `getMovieDetails throws FailedException from remote`() = runTest {
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieRemoteDataSource.getMovieDetails(
                movieId,
                "en"
            )
        } throws FailedException("")

        val result = runCatching { movieRepository.getMovieDetails(movieId) }

        assertThat(result.exceptionOrNull()).isInstanceOf(FailedException::class.java)
    }

    @Test
    fun `getMovieDetails throws FailedException from remote, safeCall should rethrow`() =
        runTest {
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieRemoteDataSource.getMovieDetails(
                    1,
                    language = "en"
                )
            } throws FailedException("")

            val result = runCatching {
                movieRepository.getMovieDetails(1)
            }

            assertThat(result.exceptionOrNull()).isInstanceOf(FailedException::class.java)
        }

    @Test
    fun `getMovieCast - should throw NoCastFoundException when remote throws it and local is empty`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getCastByMovieId(
                    movieId,
                    language
                )
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getMovieCredits(
                    movieId,
                    language
                )
            } throws FailedException(
                "No cast found"
            )

            // When & Then
            assertThrows<FailedException> {
                movieRepository.getMovieCast(movieId)
            }
        }

    @Test
    fun `getMovieGallery - should throw FailedException when remote throws it and local is null`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieRemoteDataSource.getMovieImages(movieId) } throws FailedException(
                "No gallery"
            )

            // When & Then
            assertThrows<FailedException> {
                movieRepository.getMovieGallery(movieId)
            }
        }

    @Test
    fun `getMovieDetails - should fetch from remote and save to local when local is null`() =
        runTest {
            // Given
            val expectedMovie = mockMovieDto
            val localMovieDto = expectedMovie.toLocalDto(language)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns mockMovieDto.toLocalDto(language)
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieRemoteDataSource.getMovieDetails(
                    movieId,
                    language
                )
            } returns expectedMovie
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs
            coEvery {
                movieLocalDataSource.getMovieById(
                    movieId,
                    language
                )
            } returns localMovieDto

            // When
            val result = movieRepository.getMovieDetails(movieId)

            // Then
            assertThat(result.title).isEqualTo(expectedMovie.title)
        }

    @Test
    fun `getMovieDetails - should not call remote when local data is available`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getMovieById(movieId, language)
        } returns mockMovieDto.toLocalDto(language)

        // When
        movieRepository.getMovieDetails(movieId)

        // Then
        coVerify(exactly = 0) { movieRemoteDataSource.getMovieDetails(any(), any()) }
    }

    @Test
    fun `getMovieDetails - should not insert movie when local data is available`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getMovieById(movieId, language)
        } returns mockMovieDto.toLocalDto(language)

        // When
        movieRepository.getMovieDetails(movieId)

        // Then
        coVerify(exactly = 0) { movieLocalDataSource.addMovie(any()) }
    }


    @Test
    fun `getMovieDetails - should throw FailedException when local is empty and remote fetch fails`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null

            // When & Then
            assertThrows<FailedException> {
                movieRepository.getMovieDetails(movieId)
            }
        }


    @Test
    fun `getMovieDetails - should throw NoFoundMovieException when remote fetch succeeds but local save fails to retrieve`() =
        runTest {
            // Given
            val expectedMovie = mockMovieDto

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieRemoteDataSource.getMovieDetails(
                    movieId,
                    language
                )
            } returns expectedMovie
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs
            coEvery {
                movieLocalDataSource.getMovieById(
                    movieId,
                    language
                )
            } returnsMany listOf(null, null)

            // When & Then
            assertThrows<FailedException> {
                movieRepository.getMovieDetails(movieId)
            }
        }

    @Test
    fun `getMovieCast - should return movie cast from local when available`() = runTest {
        // Given
        val expectedMovieCast = mockMovieCreditsDto.cast?.map { it.toEntity() } ?: emptyList()
        val localCast = expectedMovieCast.map { it.toLocalDto(movieId, language) }

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns localCast

        // When
        val result = movieRepository.getMovieCast(movieId)

        // Then
        assertThat(result.first().name).isEqualTo(expectedMovieCast.first().name)
    }

    @Test
    fun `getMovieCast - should not call remote when local cast is available`() = runTest {
        // Given
        val localCast = mockMovieCreditsDto.cast?.map {
            it.toEntity().toLocalDto(movieId, language)
        } ?: emptyList()

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns localCast

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 0) { movieRemoteDataSource.getMovieCredits(any(), any()) }
    }

    @Test
    fun `getMovieCast - should not insert cast when local cast is available`() = runTest {
        // Given
        val localCast = mockMovieCreditsDto.cast?.map {
            it.toEntity().toLocalDto(movieId, language)
        } ?: emptyList()

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns localCast

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 0) { movieLocalDataSource.addMovieCast(any()) }
    }

    @Test
    fun `getMovieCast - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getCastByMovieId(movieId, language)
            } returns emptyList()

            coEvery {
                movieRemoteDataSource.getMovieCredits(movieId, language)
            } returns mockMovieCreditsDto

            coEvery { movieLocalDataSource.addMovieCast(any()) } just Runs

            // When
            val result = movieRepository.getMovieCast(movieId)

            // Then
            assertThat(result).isEqualTo(emptyList<List<Cast>>())
        }

    @Test
    fun `getMovieCast - should call remote data source when local is empty`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto
        coEvery { movieLocalDataSource.addMovieCast(any()) } just Runs

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 1) {
            movieRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        }
    }

    @Test
    fun `getMovieCast - should save cast to local when fetched from remote`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto
        coEvery { movieLocalDataSource.addMovieCast(any()) } just Runs

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 1) { movieLocalDataSource.addMovieCast(any()) }
    }

    @Test
    fun `getMovieCast - should return empty list if remote returns no cast`() = runTest {
        // Given
        val emptyCreditsDto = mockMovieCreditsDto.copy(cast = null)

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns emptyCreditsDto
        coEvery { movieLocalDataSource.addMovieCast(any()) } just Runs

        // When
        val result = movieRepository.getMovieCast(movieId)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getMovieRecommendations - should return recommendations from local when available`() =
        runTest {
            // Given
            val expectedRecommendations = mockMovieSimilarsDto.movieSimilarDto ?: emptyList()
            val localRecommendations =
                expectedRecommendations.map { it.toLocalDto(movieId, page, language) }

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns localRecommendations

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result.first().title).isEqualTo(expectedRecommendations.map {
                it.toLocalDto(movieId, page, language).toEntity()
            }.first().title)
        }

    @Test
    fun `getMovieRecommendations - should not call remote source when local data is available`() =
        runTest {
            // Given
            val localRecommendations =
                (mockMovieSimilarsDto.movieSimilarDto ?: emptyList()).map {
                    it.toLocalDto(movieId, page, language)
                }

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns localRecommendations

            // When
            movieRepository.getMovieRecommendations(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieRemoteDataSource.getSimilarMovies(any(), any(), any())
            }
        }

    @Test
    fun `getMovieRecommendations - should not add data to local when local data is available`() =
        runTest {
            // Given
            val localRecommendations =
                (mockMovieSimilarsDto.movieSimilarDto ?: emptyList()).map {
                    it.toLocalDto(movieId, page, language)
                }

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns localRecommendations

            // When
            movieRepository.getMovieRecommendations(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieLocalDataSource.addSimilarMovies(any())
            }
        }

    @Test
    fun `getMovieRecommendations - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getSimilarMovies(movieId, page, language)
            } returns mockMovieSimilarsDto
            coEvery { movieLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result).isEqualTo(emptyList<List<MovieSimilar>>())
        }

    @Test
    fun `getMovieRecommendations - should call remote when local data is empty`() = runTest {
        // Given
        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getSimilarMovies(movieId, page, language)
        } returns emptyList()
        coEvery {
            movieRemoteDataSource.getSimilarMovies(movieId, page, language)
        } returns mockMovieSimilarsDto
        coEvery { movieLocalDataSource.addSimilarMovies(any()) } just Runs

        // When
        movieRepository.getMovieRecommendations(movieId, page)

        // Then
        coVerify(exactly = 1) {
            movieRemoteDataSource.getSimilarMovies(movieId, page, language)
        }
    }

    @Test
    fun `getMovieRecommendations - should save remote data to local when fetched`() = runTest {
        // Given
        val expectedRecommendations = mockMovieSimilarsDto.movieSimilarDto ?: emptyList()

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery {
            movieLocalDataSource.getSimilarMovies(movieId, page, language)
        } returns emptyList()
        coEvery {
            movieRemoteDataSource.getSimilarMovies(movieId, page, language)
        } returns mockMovieSimilarsDto
        coEvery { movieLocalDataSource.addSimilarMovies(any()) } just Runs

        // When
        movieRepository.getMovieRecommendations(movieId, page)

        // Then
        coVerify(exactly = 1) {
            movieLocalDataSource.addSimilarMovies(
                expectedRecommendations.map { it.toLocalDto(movieId, page, language) }
            )
        }
    }

    @Test
    fun `getMovieRecommendations - should return empty list if remote returns no recommendations`() =
        runTest {
            // Given
            val emptySimilarDto = mockMovieSimilarsDto.copy(movieSimilarDto = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getSimilarMovies(movieId, page, language)
            } returns emptySimilarDto
            coEvery { movieLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result).isEmpty()
        }

    @Test
    fun `getMovieRecommendations - should not save anything locally when remote returns null data`() =
        runTest {
            // Given
            val emptySimilarsDto = mockMovieSimilarsDto.copy(movieSimilarDto = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getSimilarMovies(movieId, page, language)
            } returns emptySimilarsDto
            coEvery { movieLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            movieRepository.getMovieRecommendations(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieLocalDataSource.addSimilarMovies(any())
            }
        }

    @Test
    fun `getMovieGallery - should return movie gallery from local when available`() = runTest {
        // Given
        val expectedGallery = mockMovieImagesDto.toEntity()
        val localGalleryEntity = GalleryEntity(
            images = expectedGallery.map { it.toLocalDto() },
            id = 0,
            movieId = movieId
        )

        coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
        coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

        // When
        val result = movieRepository.getMovieGallery(movieId)

        // Then
        assertThat(result).isEqualTo(expectedGallery)
    }

    @Test
    fun `getMovieGallery - should not call remote data source when local gallery is available`() =
        runTest {
            // Given
            val localGalleryEntity = GalleryEntity(
                images = mockMovieImagesDto.toEntity().map { it.toLocalDto() },
                id = 0,
                movieId = movieId
            )

            coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

            // When
            movieRepository.getMovieGallery(movieId)

            // Then
            coVerify(exactly = 0) { movieRemoteDataSource.getMovieImages(any()) }
        }

    @Test
    fun `getMovieGallery - should not save gallery locally when it already exists`() = runTest {
        // Given
        val localGalleryEntity = GalleryEntity(
            images = mockMovieImagesDto.toEntity().map { it.toLocalDto() },
            id = 0,
            movieId = movieId
        )

        coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

        // When
        movieRepository.getMovieGallery(movieId)

        // Then
        coVerify(exactly = 0) { movieLocalDataSource.addMovieGallery(any()) }
    }

    @Test
    fun `getMovieGallery - should fetch from remote and save to local when local is null`() =
        runTest {
            // Given
            val expectedGallery = mockMovieImagesDto.toEntity()
            val localGalleryEntity = GalleryEntity(
                images = expectedGallery.map { it.toLocalDto() },
                id = 0,
                movieId = movieId
            )

            coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieRemoteDataSource.getMovieImages(movieId) } returns mockMovieImagesDto
            coEvery { movieLocalDataSource.addMovieGallery(any()) } just Runs
            coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

            // When
            val result = movieRepository.getMovieGallery(movieId)

            // Then
            assertThat(result).isEqualTo(expectedGallery)
        }

    @Test
    fun `getMovieGallery - should throw NoFundGalleryMovieException when remote fetch succeeds but local save fails to retrieve`() =
        runTest {
            // Given
            coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieRemoteDataSource.getMovieImages(movieId) } returns mockMovieImagesDto
            coEvery { movieLocalDataSource.addMovieGallery(any()) } just Runs
            coEvery { movieLocalDataSource.getGalleryByMovieId(movieId) } returnsMany listOf(
                null, null
            )

            // When & Then
            assertThrows<FailedException> {
                movieRepository.getMovieGallery(movieId)
            }
        }

    @Test
    fun `getCompanyProducts - should return company products from local when available`() =
        runTest {
            // Given
            val expectedProductionCompanies =
                listOf(MovieProductionCompanyDto(name = "sonic")).map { it.toEntity() }

            val localMovieDtoWithCompanies =
                mockMovieDto.copy(productionCompanies = listOf(MovieProductionCompanyDto(name = "sonic")))
                    .toLocalDto(language)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns localMovieDtoWithCompanies

            // When
            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertThat(result).isEqualTo(expectedProductionCompanies)
        }

    @Test
    fun `getCompanyProducts - should not call remote data source when local movie is available`() =
        runTest {
            // Given
            val localMovieDtoWithCompanies =
                mockMovieDto.copy(productionCompanies = listOf(MovieProductionCompanyDto(name = "sonic")))
                    .toLocalDto(language)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns localMovieDtoWithCompanies

            // When
            movieRepository.getCompanyProducts(movieId)

            // Then
            coVerify(exactly = 0) { movieRemoteDataSource.getMovieDetails(any(), any()) }
        }

    @Test
    fun `getCompanyProducts - should not save movie locally when it already exists`() =
        runTest {
            // Given
            val localMovieDtoWithCompanies =
                mockMovieDto.copy(productionCompanies = listOf(MovieProductionCompanyDto(name = "sonic")))
                    .toLocalDto(language)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns localMovieDtoWithCompanies

            // When
            movieRepository.getCompanyProducts(movieId)

            // Then
            coVerify(exactly = 0) { movieLocalDataSource.addMovie(any()) }
        }

    @Test
    fun `getCompanyProducts - should fetch from remote and save to local when local is empty or companies are missing`() =
        runTest {
            // Given
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns mockMovieDto.toLocalDto(language).copy(productionCompanies = emptyList())

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieRemoteDataSource.getMovieDetails(movieId, language)
            } returns mockMovieDto.copy(
                productionCompanies = listOf(
                    MovieProductionCompanyDto(
                        name = "sonic"
                    )
                )
            )

            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertThat(result).isEqualTo(listOf<List<ProductionCompany>>())
        }

    @Test
    fun `getCompanyProducts - should save updated movie to local when fetched from remote`() =
        runTest {
            // Given
            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns mockMovieDto.toLocalDto(language).copy(productionCompanies = emptyList())
            coEvery {
                movieRemoteDataSource.getMovieDetails(movieId, language)
            } returns mockMovieDto.copy(
                productionCompanies = listOf(
                    MovieProductionCompanyDto(
                        name = "sonic"
                    )
                )
            )
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            movieRepository.getCompanyProducts(movieId)

            // Then
            coVerify(exactly = 1) {
                movieLocalDataSource.addMovie(any())
            }
        }

    @Test
    fun `getCompanyProducts - should return empty list if remote returns no production companies`() =
        runTest {
            // Given
            val movieDtoWithoutCompanies = mockMovieDto.copy(productionCompanies = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieRemoteDataSource.getMovieDetails(movieId, language)
            } returns movieDtoWithoutCompanies
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertThat(result).isEmpty()
        }

    @Test
    fun `getCompanyProducts - should not save to local if remote production companies are null`() =
        runTest {
            // Given
            val movieDtoWithoutCompanies = mockMovieDto.copy(productionCompanies = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieRemoteDataSource.getMovieDetails(movieId, language)
            } returns movieDtoWithoutCompanies
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            movieRepository.getCompanyProducts(movieId)

            // Then
            coVerify(exactly = 0) {
                movieLocalDataSource.addMovie(any())
            }
        }

    @Nested
    inner class MovieReviewsTest{
        @Test
        fun `getMovieReview - should return movie reviews from local when available`() = runTest {
            // Given
            val expectedReviews = listOf(review.toEntity())
            val localReviews = expectedReviews.map { it.toLocalDto(movieId, language) }

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getReviewsByMovieId(movieId, language)
            } returns localReviews

            // When
            val result = movieRepository.getMovieReview(movieId, page)

            // Then
            assertThat(result).isEqualTo(expectedReviews)
        }

        @Test
        fun `getMovieReview - should not call remote when local reviews are available`() = runTest {
            // Given
            val expectedReviews = listOf(review.toEntity())
            val localReviews = expectedReviews.map { it.toLocalDto(movieId, language) }

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getReviewsByMovieId(movieId, language)
            } returns localReviews

            // When
            movieRepository.getMovieReview(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieRemoteDataSource.getMovieReviews(any(), any(), any())
            }
        }

        @Test
        fun `getMovieReview - should not add review when local reviews are available`() = runTest {
            // Given
            val expectedReviews = listOf(review.toEntity())
            val localReviews = expectedReviews.map { it.toLocalDto(movieId, language) }

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getReviewsByMovieId(movieId, language)
            } returns localReviews

            // When
            movieRepository.getMovieReview(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieLocalDataSource.addMovieReviews(any())
            }
        }

        @Test
        fun `getMovieReview - should return empty list if remote returns no reviews`() = runTest {
            // Given
            val emptyReviewsDto = MovieReviewsDto(results = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getReviewsByMovieId(movieId, language)
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getMovieReviews(movieId, page, language)
            } returns emptyReviewsDto
            coEvery { movieLocalDataSource.addMovieReviews(any()) } just Runs

            // When
            val result = movieRepository.getMovieReview(movieId, page)

            // Then
            assertThat(result).isEmpty()
        }

        @Test
        fun `getMovieReview - should call remote once when local reviews are empty`() = runTest {
            // Given
            val emptyReviewsDto = MovieReviewsDto(results = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getReviewsByMovieId(movieId, language)
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getMovieReviews(movieId, page, language)
            } returns emptyReviewsDto
            coEvery { movieLocalDataSource.addMovieReviews(any()) } just Runs

            // When
            movieRepository.getMovieReview(movieId, page)

            // Then
            coVerify(exactly = 1) {
                movieRemoteDataSource.getMovieReviews(movieId, page, language)
            }
        }

        @Test
        fun `getMovieReview - should not add review when remote returns no reviews`() = runTest {
            // Given
            val emptyReviewsDto = MovieReviewsDto(results = null)

            coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
            coEvery {
                movieLocalDataSource.getReviewsByMovieId(movieId, language)
            } returns emptyList()
            coEvery {
                movieRemoteDataSource.getMovieReviews(movieId, page, language)
            } returns emptyReviewsDto
            coEvery { movieLocalDataSource.addMovieReviews(any()) } just Runs

            // When
            movieRepository.getMovieReview(movieId, page)

            // Then
            coVerify(exactly = 0) { movieLocalDataSource.addMovieReviews(any()) }
        }

        @Test
        fun `getMovieReview - should fetch from remote and save to local when local is empty`() =
            runTest {
                // Given
                val remoteReviews = listOf(reviewRemoteDto)

                coEvery { settingLocalDataSource.getLanguage() } returns MutableStateFlow("en")
                coEvery {
                    movieLocalDataSource.getReviewsByMovieId(movieId, language)
                } returns emptyList() andThen listOf(reviewRemoteDto).map {
                    it.toEntity().toLocalDto(movieId, language)
                }
                coEvery {
                    movieRemoteDataSource.getMovieReviews(movieId, page, language)
                } returns MovieReviewsDto(results = listOf(reviewRemoteDto))
                coEvery {
                    movieLocalDataSource.addMovieReviews(any())
                } just Runs


                // When
                val result = movieRepository.getMovieReview(movieId, page)

                // Then
                assertThat(result.first().name).isEqualTo(remoteReviews.map { it.toEntity() }
                    .first().name)
            }
    }


    @Test
    fun `getTrailerVideoForMovie - should return trailers from remote when network is available`() =
        runTest {
            // Given
            val expectedTrailers =
                mockMovieVideosDto.movieVideoResultDto?.map { it.toEntity() } ?: emptyList()

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery { movieRemoteDataSource.getTrailerVideoForMovie(movieId) } returns mockMovieVideosDto

            // When
            val result = movieRepository.getTrailerVideoForMovie(movieId)

            // Then
            assertThat(result).isEqualTo(expectedTrailers)
        }

    @Test
    fun `getTrailerVideoForMovie - should call remote data source exactly once`() = runTest {
        // Given
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { movieRemoteDataSource.getTrailerVideoForMovie(movieId) } returns mockMovieVideosDto

        // When
        movieRepository.getTrailerVideoForMovie(movieId)

        // Then
        coVerify(exactly = 1) { movieRemoteDataSource.getTrailerVideoForMovie(movieId) }
    }


    @Test
    fun `getTrailerVideoForMovie - should return empty list when remote returns no trailers`() =
        runTest {
            // Given
            val emptyVideosDto = mockMovieVideosDto.copy(movieVideoResultDto = null)

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery { movieRemoteDataSource.getTrailerVideoForMovie(movieId) } returns emptyVideosDto

            // When
            val result = movieRepository.getTrailerVideoForMovie(movieId)

            // Then
            assertThat(result).isEmpty()
            coVerify(exactly = 1) { movieRemoteDataSource.getTrailerVideoForMovie(movieId) }
        }

    @Test
    fun `getTrailerVideoForMovie - should call remote even when remote returns no trailers`() =
        runTest {
            // Given
            val emptyVideosDto = mockMovieVideosDto.copy(movieVideoResultDto = null)

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery { movieRemoteDataSource.getTrailerVideoForMovie(movieId) } returns emptyVideosDto

            // When
            movieRepository.getTrailerVideoForMovie(movieId)

            // Then
            coVerify(exactly = 1) { movieRemoteDataSource.getTrailerVideoForMovie(movieId) }
        }

    @Test
    fun `getTrailerVideoForMovie - should throw NoInternetConnectionException when network is unavailable`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When & Then
            assertThrows<NoInternetConnectionException> {
                movieRepository.getTrailerVideoForMovie(movieId)
            }
        }

    @Test
    fun `getTrailerVideoForMovie - should not call remote data source when network is unavailable`() =
        runTest {
            // Given
            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

            // When
            runCatching { movieRepository.getTrailerVideoForMovie(movieId) }

            // Then
            coVerify(exactly = 0) { movieRemoteDataSource.getTrailerVideoForMovie(any()) }
        }

    @Test
    fun `deleteMovieRating should succeed when remote call succeeds`() = runTest {
        // Given
        coEvery { movieRemoteDataSource.deleteMovieRating(movieId = 550) } coAnswers {true}

        // When
        val result = runCatching {
            movieRepository.deleteMovieRating(550)
        }

        // Then
        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { movieRemoteDataSource.deleteMovieRating(movieId = 550) }
    }

    @Test
    fun `deleteMovieRating should throw FailedException when remote throws exception`() = runTest {
        // Given
        val causeException = RuntimeException("Network error")
        coEvery { movieRemoteDataSource.deleteMovieRating(movieId = 550) } throws causeException

        // When & Then
        val result = runCatching {
            movieRepository.deleteMovieRating(550)
        }

        assertThat(result.exceptionOrNull()).isInstanceOf(FailedException::class.java)
        coVerify(exactly = 1) { movieRemoteDataSource.deleteMovieRating(movieId = 550) }
    }

    @Test
    fun `deleteMovieRating should throw NoInternetConnectionException when offline`() = runTest {
        // Given
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(false)

        // When & Then
        assertThrows<NoInternetConnectionException> {
            movieRepository.deleteMovieRating(550)
        }
        coVerify(exactly = 0) { movieRemoteDataSource.deleteMovieRating(any()) }
    }

    private companion object {
        const val movieId = 550
        const val language = "en"
        const val page = 1
    }
}