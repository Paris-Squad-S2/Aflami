package com.repository.movie.repository

import com.domain.mediaDetails.exception.NetworkException
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
import com.repository.movie.testUtils.review
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
    fun `getMovieDetails - should return movie details from local when available`() = runTest {
        // Given
        val movieId = 550
        val language = "en"
        val expectedMovie = mockMovieDto

        coEvery {
            movieLocalDataSource.getMovieById(
                movieId,
                language
            )
        } returns mockMovieDto.toLocalDto(language)

        // When
        val result = movieRepository.getMovieDetails(movieId)

        // Then
        assertThat(result.title).isEqualTo(expectedMovie.title)
        coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieDetails(any(), any()) }
        coVerify(exactly = 0) { movieLocalDataSource.addMovie(any()) }
    }


    @Test
    fun `getMovieDetails - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            val movieId = 550
            val language = "en"

            // When & Then
            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            assertThrows<NetworkException> {
                movieRepository.getMovieDetails(movieId)
            }
        }

    @Test
    fun `getMovieCast - should return movie cast from local when available`() = runTest {
        // Given
        val movieId = 550
        val language = "en"
        val expectedMovieCast = mockMovieCreditsDto.cast?.map { it.toEntity() } ?: emptyList()
        val localCast = expectedMovieCast.map { it.toLocalDto(movieId, language) }

        coEvery { movieCastLocalDataSource.getCastByMovieId(movieId, language) } returns localCast

        // When
        val result = movieRepository.getMovieCast(movieId)

        // Then
        assertThat(result.first().name).isEqualTo(expectedMovieCast.first().name)
        coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieCredits(any(), any()) }
        coVerify(exactly = 0) { movieCastLocalDataSource.addCast(any()) }
    }

    @Test
    fun `getMovieCast - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            val movieId = 550
            val language = "en"

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
            coVerify(exactly = 1) {
                movieDetailsRemoteDataSource.getMovieCredits(
                    movieId,
                    language
                )
            }
            coVerify(exactly = 1) { movieCastLocalDataSource.addCast(any()) }
        }

    @Test
    fun `getMovieCast - should return empty list if remote returns no cast`() = runTest {
        // Given
        val movieId = 550
        val language = "en"
        val emptyCreditsDto = mockMovieCreditsDto.copy(cast = null)

        coEvery { movieCastLocalDataSource.getCastByMovieId(movieId, language) } returns emptyList()
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
        coVerify(exactly = 1) { movieDetailsRemoteDataSource.getMovieCredits(movieId, language) }
        coVerify(exactly = 1) { movieCastLocalDataSource.addCast(emptyList()) }
    }

    @Test
    fun `getMovieRecommendations - should return recommendations from local when available`() =
        runTest {
            // Given
            val movieId = 123
            val page = 1
            val language = "en"
            val expectedRecommendations =
                mockMovieSimilarsDto.movieSimilarDto ?: emptyList()
            val localRecommendations =
                expectedRecommendations.map { it.toLocalDto(movieId, page, language) }

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(movieId, page, language)
            } returns localRecommendations

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result.first().title).isEqualTo(expectedRecommendations.map {
                it.toLocalDto(
                    movieId,
                    page,
                    language
                ).toEntity()
            }.first().title)
            coVerify(exactly = 0) {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    any(),
                    any(),
                    any()
                )
            }
            coVerify(exactly = 0) { movieSimilarLocalDataSource.addSimilarMovies(any()) }
        }

    @Test
    fun `getMovieRecommendations - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            val movieId = 123
            val page = 1
            val language = "en"
            val expectedRecommendations =
                mockMovieSimilarsDto.movieSimilarDto ?: emptyList()

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            } returns emptyList()
            coEvery {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            } returns mockMovieSimilarsDto
            coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result).isEqualTo(emptyList<List<MovieSimilar>>())
            coVerify(exactly = 1) {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            }
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
            val movieId = 123
            val page = 1
            val language = "en"
            val emptySimilarsDto = mockMovieSimilarsDto.copy(movieSimilarDto = null)

            coEvery {
                movieSimilarLocalDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            } returns emptyList()
            coEvery {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            } returns emptySimilarsDto
            coEvery { movieSimilarLocalDataSource.addSimilarMovies(any()) } just Runs

            // When
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertThat(result).isEmpty()
            coVerify(exactly = 1) {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            }
            coVerify(exactly = 0) { movieSimilarLocalDataSource.addSimilarMovies(any()) }
        }

    @Test
    fun `getMovieGallery - should return movie gallery from local when available`() = runTest {
        // Given
        val movieId = 123
        val expectedGallery = mockMovieImagesDto.toEntity()
        val localGalleryEntity = GalleryEntity(
            images = expectedGallery.movieImages.map { it.toLocalDto() },
            id = 0,
            movieId = movieId
        )

        coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns localGalleryEntity

        // When
        val result = movieRepository.getMovieGallery(movieId)

        // Then
        assertThat(result.movieImages).isEqualTo(expectedGallery.movieImages)
        coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieImages(any()) }
        coVerify(exactly = 0) { movieGalleryLocalDataSource.addGallery(any()) }
    }

    @Test
    fun `getMovieGallery - should fetch from remote and save to local when local is empty`() =
        runTest {
            // Given
            val movieId = 123

            coEvery { movieGalleryLocalDataSource.getGalleryByMovieId(movieId) } returns null
            coEvery { movieDetailsRemoteDataSource.getMovieImages(movieId) } returns mockMovieImagesDto
            coEvery { movieGalleryLocalDataSource.addGallery(any()) } just Runs

            // When & Then
            assertThrows<NetworkException> {
                movieRepository.getMovieGallery(movieId)
            }
        }

    @Test
    fun `getCompanyProducts - should return company products from local when available`() =
        runTest {
            // Given
            val movieId = 123
            val language = "en"
            val expectedProductionCompanies =
                listOf(MovieProductionCompanyDto(name = "sonic")).map { it.toEntity() }
            val localMovieDtoWithCompanies =
                mockMovieDto.copy(productionCompanies = listOf(MovieProductionCompanyDto(name = "sonic")))
                    .toLocalDto(language)

            coEvery {
                movieLocalDataSource.getMovieById(
                    movieId,
                    language
                )
            } returns localMovieDtoWithCompanies

            // When
            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertThat(result).isEqualTo(expectedProductionCompanies)
            coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieDetails(any(), any()) }
            coVerify(exactly = 0) { movieLocalDataSource.addMovie(any()) }
        }

    @Test
    fun `getCompanyProducts - should fetch from remote and save to local when local is empty or companies are missing`() =
        runTest {
            // Given
            val movieId = 123
            val language = "en"

            coEvery {
                movieLocalDataSource.getMovieById(movieId, language)
            } returns mockMovieDto.toLocalDto(language).copy(productionCompanies = emptyList())
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
            } returns mockMovieDto.copy(productionCompanies = listOf(MovieProductionCompanyDto(name = "sonic")))
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertThat(result).isEqualTo(listOf<List<ProductionCompany>>())
            coVerify(exactly = 1) {
                movieDetailsRemoteDataSource.getMovieDetails(
                    movieId,
                    language
                )
            }
            coVerify(exactly = 1) { movieLocalDataSource.addMovie(any()) }

        }

    @Test
    fun `getCompanyProducts - should return empty list if remote returns no production companies`() =
        runTest {
            // Given
            val movieId = 123
            val language = "en"
            val movieDtoWithoutCompanies = mockMovieDto.copy(productionCompanies = null)

            coEvery { movieLocalDataSource.getMovieById(movieId, language) } returns null
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(
                    movieId,
                    language
                )
            } returns movieDtoWithoutCompanies
            coEvery { movieLocalDataSource.addMovie(any()) } just Runs

            // When
            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertThat(result).isEmpty()
            coVerify(exactly = 1) {
                movieDetailsRemoteDataSource.getMovieDetails(
                    movieId,
                    language
                )
            }
            coVerify(exactly = 0) { movieLocalDataSource.addMovie(any()) }
        }

    @Test
    fun `getMovieReview - should return movie reviews from local when available`() = runTest {
        // Given
        val movieId = 123
        val language = "en"
        val page = 1
        val expectedReviews = listOf(review.toEntity())
        val localReviews = expectedReviews.map { it.toLocalDto(movieId, language) }

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(
                movieId,
                language
            )
        } returns localReviews

        // When
        val result = movieRepository.getMovieReview(movieId, page)

        // Then
        assertThat(result).isEqualTo(expectedReviews)
        coVerify(exactly = 0) { movieDetailsRemoteDataSource.getMovieReviews(any(), any(), any()) }
        coVerify(exactly = 0) { movieReviewLocalDataSource.addReview(any()) }
    }

    @Test
    fun `getMovieReview - should return empty list if remote returns no reviews`() = runTest {
        // Given
        val movieId = 123
        val language = "en"
        val page = 1
        val emptyReviewsDto = MovieReviewsDto(results = null)

        coEvery {
            movieReviewLocalDataSource.getReviewsForMovie(
                movieId,
                language
            )
        } returns emptyList()
        coEvery {
            movieDetailsRemoteDataSource.getMovieReviews(
                movieId,
                page,
                language
            )
        } returns emptyReviewsDto
        coEvery { movieReviewLocalDataSource.addReview(any()) } just Runs

        // When
        val result = movieRepository.getMovieReview(movieId, page)

        // Then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) {
            movieDetailsRemoteDataSource.getMovieReviews(
                movieId,
                page,
                language
            )
        }
        coVerify(exactly = 0) { movieReviewLocalDataSource.addReview(any()) }
    }
}