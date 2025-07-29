package com.repository.movie.repository

import com.domain.mediaDetails.exception.NoCastFoundException
import com.domain.mediaDetails.exception.NoGalleryFoundException
import com.domain.mediaDetails.exception.NoInternetConnectionException
import com.domain.mediaDetails.exception.NoMovieFoundException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.MovieSimilar
import com.domain.mediaDetails.model.ProductionCompany
import com.google.common.truth.Truth.assertThat
import com.repository.movie.dataSource.local.MovieCastLocalDataSource
import com.repository.movie.dataSource.local.MovieGalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.MovieReviewLocalDataSource
import com.repository.movie.dataSource.local.MovieSimilarLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.mapper.toLocalDto
import com.repository.movie.models.local.GalleryEntity
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
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class MovieRepositoryImplTest {
    private lateinit var movieRepository: MovieRepositoryImpl
    private var movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource = mockk(relaxed = true)
    private var movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
    private var movieCastLocalDataSource: MovieCastLocalDataSource = mockk(relaxed = true)
    private var movieGalleryLocalDataSource: MovieGalleryLocalDataSource = mockk(relaxed = true)
    private var movieReviewLocalDataSource: MovieReviewLocalDataSource = mockk(relaxed = true)
    private var networkConnectionChecker: NetworkConnectionChecker = mockk(relaxed = true)
    private var movieSimilarLocalDataSource: MovieSimilarLocalDataSource = mockk()

    @BeforeEach
    fun setUp() {
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)

        movieRepository = MovieRepositoryImpl(
            networkConnectionChecker,
            movieLocalDataSource,
            movieCastLocalDataSource,
            movieGalleryLocalDataSource,
            movieReviewLocalDataSource,
            movieDetailsRemoteDataSource,
            movieSimilarLocalDataSource
        )
    }

    @Test
    fun `getMovieCast - should throw NoCastFoundException when remote throws it and local is empty`() =
        runTest {
            // Given
            coEvery {
                movieCastLocalDataSource.getCastByMovieId(
                    movieId,
                    language
                )
            } returns emptyList()
            coEvery {
                movieDetailsRemoteDataSource.getMovieCredits(
                    movieId,
                    language
                )
            } throws NoCastFoundException(
                "No cast found"
            )

            // When & Then
            assertThrows<NoCastFoundException> {
                movieRepository.getMovieCast(movieId)
            }
        }

    @Test
    fun `getMovieGallery - should throw NoGalleryFoundException when remote throws it and local is null`() =
        runTest {
            // Given
            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieDetailsRemoteDataSource.getMovieImages(movieId) } throws NoGalleryFoundException(
                "No gallery"
            )

            // When & Then
            assertThrows<NoGalleryFoundException> {
                movieRepository.getMovieGallery(movieId)
            }
        }
    @Test
    fun `getMovieDetails - should fetch from remote and save to local when local is null`() =
        runTest {
            // Given
            val expectedMovie = mockMovieDto
            val localMovieDto = expectedMovie.toLocalDto(language)

            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns mockMovieDto.toLocalDto(language)
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(
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
        coEvery {
            movieLocalDataSource.getMovieById(movieId, language)
        } returns mockMovieDto.toLocalDto(language)

        // When
        movieRepository.getMovieDetails(movieId)

        // Then
        coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieDetails(any(), any()) }
    }

    @Test
    fun `getMovieDetails - should not insert movie when local data is available`() = runTest {
        // Given
        coEvery {
            movieLocalDataSource.getMovieById(movieId, language)
        } returns mockMovieDto.toLocalDto(language)

        // When
        movieRepository.getMovieDetails(movieId)

        // Then
        coVerify(exactly = 0) { movieLocalDataSource.addMovie(any()) }
    }


    @Test
    fun `getMovieDetails - should throw NoMovieFoundException when local is empty and remote fetch fails`() =
        runTest {
            // Given
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null

            // When & Then
            assertThrows<NoMovieFoundException> {
                movieRepository.getMovieDetails(movieId)
            }
        }


    @Test
    fun `getMovieDetails - should throw NoFoundMovieException when remote fetch succeeds but local save fails to retrieve`() =
        runTest {
            // Given
            val expectedMovie = mockMovieDto

            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(
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
            assertThrows<NoMovieFoundException> {
                movieRepository.getMovieDetails(movieId)
            }
        }

    @Test
    fun `getMovieCast - should return movie cast from local when available`() = runTest {
        // Given
        val expectedMovieCast = mockMovieCreditsDto.cast?.map { it.toEntity() } ?: emptyList()
        val localCast = expectedMovieCast.map { it.toLocalDto(movieId, language) }

        coEvery {
            movieCastLocalDataSource.getCastByMovieId(
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

        coEvery {
            movieCastLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns localCast

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieCredits(any(), any()) }
    }

    @Test
    fun `getMovieCast - should not insert cast when local cast is available`() = runTest {
        // Given
        val localCast = mockMovieCreditsDto.cast?.map {
            it.toEntity().toLocalDto(movieId, language)
        } ?: emptyList()

        coEvery {
            movieCastLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns localCast

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 0) { movieCastLocalDataSource.addCast(any()) }
    }

    @Test
    fun `getMovieCast - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            coEvery {
                movieCastLocalDataSource.getCastByMovieId(movieId, language)
            } returns emptyList()

            coEvery {
                movieDetailsRemoteDataSource.getMovieCredits(movieId, language)
            } returns mockMovieCreditsDto

            coEvery { movieCastLocalDataSource.addCast(any()) } just Runs

            // When
            val result = movieRepository.getMovieCast(movieId)

            // Then
            assertThat(result).isEqualTo(emptyList<List<Cast>>())
        }

    @Test
    fun `getMovieCast - should call remote data source when local is empty`() = runTest {
        // Given
        coEvery {
            movieCastLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto
        coEvery { movieCastLocalDataSource.addCast(any()) } just Runs

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 1) {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        }
    }

    @Test
    fun `getMovieCast - should save cast to local when fetched from remote`() = runTest {
        // Given
        coEvery {
            movieCastLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto
        coEvery { movieCastLocalDataSource.addCast(any()) } just Runs

        // When
        movieRepository.getMovieCast(movieId)

        // Then
        coVerify(exactly = 1) { movieCastLocalDataSource.addCast(any()) }
    }

    @Test
    fun `getMovieCast - should return empty list if remote returns no cast`() = runTest {
        // Given
        val emptyCreditsDto = mockMovieCreditsDto.copy(cast = null)

        coEvery {
            movieCastLocalDataSource.getCastByMovieId(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns emptyCreditsDto
        coEvery { movieCastLocalDataSource.addCast(any()) } just Runs

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

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
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

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns localRecommendations

            // When
            movieRepository.getMovieRecommendations(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieDetailsRemoteDataSource.getSimilarMovies(any(), any(), any())
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

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns localRecommendations

            // When
            movieRepository.getMovieRecommendations(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieSimilarLocalDataSource.addSimilarMovies(any())
            }
        }

    @Test
    fun `getMovieRecommendations - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns emptyList()
            coEvery {
                movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)
            } returns mockMovieSimilarsDto
            coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result).isEqualTo(emptyList<List<MovieSimilar>>())
        }

    @Test
    fun `getMovieRecommendations - should call remote when local data is empty`() = runTest {
        // Given
        coEvery {
            movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)
        } returns mockMovieSimilarsDto
        coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

        // When
        movieRepository.getMovieRecommendations(movieId, page)

        // Then
        coVerify(exactly = 1) {
            movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)
        }
    }

    @Test
    fun `getMovieRecommendations - should save remote data to local when fetched`() = runTest {
        // Given
        val expectedRecommendations = mockMovieSimilarsDto.movieSimilarDto ?: emptyList()

        coEvery {
            movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)
        } returns mockMovieSimilarsDto
        coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

        // When
        movieRepository.getMovieRecommendations(movieId, page)

        // Then
        coVerify(exactly = 1) {
            movieSimilarLocalDataSource.addSimilarMovies(
                expectedRecommendations.map { it.toLocalDto(movieId, page, language) }
            )
        }
    }

    @Test
    fun `getMovieRecommendations - should return empty list if remote returns no recommendations`() =
        runTest {
            // Given
            val emptySimilarDto = mockMovieSimilarsDto.copy(movieSimilarDto = null)

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns emptyList()
            coEvery {
                movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)
            } returns emptySimilarDto
            coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

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

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns emptyList()
            coEvery {
                movieDetailsRemoteDataSource.getSimilarMovies(movieId, page, language)
            } returns emptySimilarsDto
            coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            movieRepository.getMovieRecommendations(movieId, page)

            // Then
            coVerify(exactly = 0) {
                movieSimilarLocalDataSource.addSimilarMovies(any())
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

        coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

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

            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

            // When
            movieRepository.getMovieGallery(movieId)

            // Then
            coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieImages(any()) }
        }

    @Test
    fun `getMovieGallery - should not save gallery locally when it already exists`() = runTest {
        // Given
        val localGalleryEntity = GalleryEntity(
            images = mockMovieImagesDto.toEntity().map { it.toLocalDto() },
            id = 0,
            movieId = movieId
        )

        coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

        // When
        movieRepository.getMovieGallery(movieId)

        // Then
        coVerify(exactly = 0) { movieGalleryLocalDataSource.addGallery(any()) }
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

            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieDetailsRemoteDataSource.getMovieImages(movieId) } returns mockMovieImagesDto
            coEvery { movieGalleryLocalDataSource.addGallery(any()) } just Runs
            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

            // When
            val result = movieRepository.getMovieGallery(movieId)

            // Then
            assertThat(result).isEqualTo(expectedGallery)
        }

    @Test
    fun `getMovieGallery - should throw NoFundGalleryMovieException when remote fetch succeeds but local save fails to retrieve`() =
        runTest {
            // Given
            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieDetailsRemoteDataSource.getMovieImages(movieId) } returns mockMovieImagesDto
            coEvery { movieGalleryLocalDataSource.addGallery(any()) } just Runs
            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returnsMany listOf(
                null, null
            )

            // When & Then
            assertThrows<NoGalleryFoundException> {
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

            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns localMovieDtoWithCompanies

            // When
            movieRepository.getCompanyProducts(movieId)

            // Then
            coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieDetails(any(), any()) }
        }

    @Test
    fun `getCompanyProducts - should not save movie locally when it already exists`() =
        runTest {
            // Given
            val localMovieDtoWithCompanies =
                mockMovieDto.copy(productionCompanies = listOf(MovieProductionCompanyDto(name = "sonic")))
                    .toLocalDto(language)

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

            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
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
            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns mockMovieDto.toLocalDto(language).copy(productionCompanies = emptyList())
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
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

            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
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

            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
            } returns movieDtoWithoutCompanies
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            movieRepository.getCompanyProducts(movieId)

            // Then
            coVerify(exactly = 0) {
                movieLocalDataSource.addMovie(any())
            }
        }

    @Test
    fun `getMovieReview - should return movie reviews from local when available`() = runTest {
        // Given
        val expectedReviews = listOf(review.toEntity())
        val localReviews = expectedReviews.map { it.toLocalDto(movieId, language) }

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
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

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
        } returns localReviews

        // When
        movieRepository.getMovieReview(movieId, page)

        // Then
        coVerify(exactly = 0) {
            movieDetailsRemoteDataSource.getMovieReviews(any(), any(), any())
        }
    }

    @Test
    fun `getMovieReview - should not add review when local reviews are available`() = runTest {
        // Given
        val expectedReviews = listOf(review.toEntity())
        val localReviews = expectedReviews.map { it.toLocalDto(movieId, language) }

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
        } returns localReviews

        // When
        movieRepository.getMovieReview(movieId, page)

        // Then
        coVerify(exactly = 0) {
            movieReviewLocalDataSource.addReview(any())
        }
    }

    @Test
    fun `getMovieReview - should return empty list if remote returns no reviews`() = runTest {
        // Given
        val emptyReviewsDto = MovieReviewsDto(results = null)

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
        } returns emptyReviewsDto
        coEvery { movieReviewLocalDataSource.addReview(any()) } just Runs

        // When
        val result = movieRepository.getMovieReview(movieId, page)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getMovieReview - should call remote once when local reviews are empty`() = runTest {
        // Given
        val emptyReviewsDto = MovieReviewsDto(results = null)

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
        } returns emptyReviewsDto
        coEvery { movieReviewLocalDataSource.addReview(any()) } just Runs

        // When
        movieRepository.getMovieReview(movieId, page)

        // Then
        coVerify(exactly = 1) {
            movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
        }
    }

    @Test
    fun `getMovieReview - should not add review when remote returns no reviews`() = runTest {
        // Given
        val emptyReviewsDto = MovieReviewsDto(results = null)

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
        } returns emptyReviewsDto
        coEvery { movieReviewLocalDataSource.addReview(any()) } just Runs

        // When
        movieRepository.getMovieReview(movieId, page)

        // Then
        coVerify(exactly = 0) { movieReviewLocalDataSource.addReview(any()) }
    }

    @Test
    fun `getMovieReview - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            val remoteReviews = listOf(reviewRemoteDto)

            coEvery {
                movieReviewLocalDataSource.getReviewsForMovie(movieId, language)
            } returns emptyList() andThen listOf(reviewRemoteDto).map {
                it.toEntity().toLocalDto(movieId, language)
            }
            coEvery {
                movieDetailsRemoteDataSource.getMovieReviews(movieId, page, language)
            } returns MovieReviewsDto(results = listOf(reviewRemoteDto))
            coEvery {
                movieReviewLocalDataSource.addReview(any())
            } just Runs


            // When
            val result = movieRepository.getMovieReview(movieId, page)

            // Then
            assertThat(result.first().name).isEqualTo(remoteReviews.map { it.toEntity() }
                .first().name)
        }


    @Test
    fun `getTrailerVideoForMovie - should return trailers from remote when network is available`() =
        runTest {
            // Given
            val expectedTrailers =
                mockMovieVideosDto.movieVideoResultDto?.map { it.toEntity() } ?: emptyList()

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) } returns mockMovieVideosDto

            // When
            val result = movieRepository.getTrailerVideoForMovie(movieId)

            // Then
            assertThat(result).isEqualTo(expectedTrailers)
        }

    @Test
    fun `getTrailerVideoForMovie - should call remote data source exactly once`() = runTest {
        // Given
        coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
        coEvery { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) } returns mockMovieVideosDto

        // When
        movieRepository.getTrailerVideoForMovie(movieId)

        // Then
        coVerify(exactly = 1) { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) }
    }


    @Test
    fun `getTrailerVideoForMovie - should return empty list when remote returns no trailers`() =
        runTest {
            // Given
            val emptyVideosDto = mockMovieVideosDto.copy(movieVideoResultDto = null)

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) } returns emptyVideosDto

            // When
            val result = movieRepository.getTrailerVideoForMovie(movieId)

            // Then
            assertThat(result).isEmpty()
            coVerify(exactly = 1) { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) }
        }

    @Test
    fun `getTrailerVideoForMovie - should call remote even when remote returns no trailers`() =
        runTest {
            // Given
            val emptyVideosDto = mockMovieVideosDto.copy(movieVideoResultDto = null)

            coEvery { networkConnectionChecker.isConnected } returns MutableStateFlow(true)
            coEvery { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) } returns emptyVideosDto

            // When
            movieRepository.getTrailerVideoForMovie(movieId)

            // Then
            coVerify(exactly = 1) { movieDetailsRemoteDataSource.getTrailerVideoForMovie(movieId) }
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
            coVerify(exactly = 0) { movieDetailsRemoteDataSource.getTrailerVideoForMovie(any()) }
        }


    private companion object {
        const val movieId = 550
        const val language = "en"
        const val page = 1
    }
}