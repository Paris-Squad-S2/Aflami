package com.repository.movie.repository

import com.domain.mediaDetails.exception.NoGalleryMovieDetailsFoundException
import com.domain.mediaDetails.exception.NoMovieDetailsFoundException
import com.domain.mediaDetails.model.Cast
import com.domain.mediaDetails.model.Gallery
import com.domain.mediaDetails.model.Review
import com.repository.movie.dataSource.local.CastLocalDataSource
import com.repository.movie.dataSource.local.GalleryLocalDataSource
import com.repository.movie.dataSource.local.MovieLocalDataSource
import com.repository.movie.dataSource.local.ReviewLocalDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.repository.movie.mapper.toEntity
import com.repository.movie.mapper.toLocalDto
import com.repository.movie.mapper.toRemoteDto
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.remote.MovieProductionCompanyDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.testUtils.mockMovieCreditsDto
import com.repository.movie.testUtils.mockMovieDto
import com.repository.movie.testUtils.mockMovieImagesDto
import com.repository.movie.testUtils.mockMovieSimilarsDto
import com.repository.movie.testUtils.review
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class MovieRepositoryImplTest {
    private lateinit var movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource
    private lateinit var movieRepository: MovieRepositoryImpl
    private lateinit var movieLocalDataSource: MovieLocalDataSource
    private lateinit var castLocalDataSource: CastLocalDataSource
    private lateinit var galleryLocalDataSource: GalleryLocalDataSource
    private lateinit var reviewLocalDataSource: ReviewLocalDataSource

    @BeforeEach
    fun setUp() {
        movieDetailsRemoteDataSource = mockk<MovieDetailsRemoteDataSource>()
        movieLocalDataSource = mockk<MovieLocalDataSource>()
        castLocalDataSource = mockk<CastLocalDataSource>()
        galleryLocalDataSource = mockk<GalleryLocalDataSource>()
        reviewLocalDataSource = mockk<ReviewLocalDataSource>()
        movieRepository = MovieRepositoryImpl(
            movieLocalDataSource,
            castLocalDataSource,
            galleryLocalDataSource,
            reviewLocalDataSource,
            movieDetailsRemoteDataSource
        )
    }

    @Test
    fun `getMovieDetails - should return movie details when API delivers the goods`() = runTest {
        // Given
        val movieId = 550
        val language = "en"
        val expectedMovie = mockMovieDto.toEntity()

        coEvery {
            movieDetailsRemoteDataSource.getMovieDetails(
                movieId,
                language
            )
        } returns mockMovieDto

        coEvery { movieLocalDataSource.getMovie(movieId) } returns mockMovieDto.toLocalDto()
        coEvery { movieLocalDataSource.addMovie(any()) } returns Unit

        // When
        val result = movieRepository.getMovieDetails(movieId)

        // Then
        assertEquals(expectedMovie.title, result.title)
    }

    @Test
    fun `getMovieDetails - should throw MovieDetailsFoundException when local is empty`() =
        runTest {
            // Given
            val movieId = 550
            val language = "en"

            // When && Then
            coEvery { movieLocalDataSource.getMovie(movieId) } returns null andThen mockMovieDto.toLocalDto()

            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(movieId, language)
            } returns mockMovieDto

            coEvery { movieLocalDataSource.addMovie(any()) } returns Unit
            assertThrows<NoMovieDetailsFoundException> {
                movieRepository.getMovieDetails(movieId)
            }

        }

    @Test
    fun `getMovieCast - should return movie cast when API delivers the goods`() = runTest {
        // Given
        val movieId = 550
        val language = "en"

        val expectedMovieCast = mockMovieCreditsDto.cast?.map { it.toEntity() } ?: emptyList()

        // When
        coEvery {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto
        coEvery { castLocalDataSource.addCast(any()) } returns Unit

        val castLocal =
            mockMovieCreditsDto.cast?.map { it.toEntity() }?.map { it.toLocalDto() } ?: emptyList()

        coEvery { castLocalDataSource.getCastByMovieId(movieId) } returns castLocal

        val result = movieRepository.getMovieCast(movieId)

        // Then
        assertEquals(expectedMovieCast, result)

    }

    @Test
    fun `getMovieCast - should return movie cast from remote when local is empty`() = runTest {
        // Given
        val movieId = 550
        val language = "en"


        // When
        coEvery {
            castLocalDataSource.getCastByMovieId(movieId)
        } returns emptyList() andThen emptyList()

        coEvery {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto

        coEvery { castLocalDataSource.addCast(any()) } returns Unit

        val result = movieRepository.getMovieCast(movieId)

        // Then
        coVerify {
            castLocalDataSource.addCast(any())
        }
        assertEquals(emptyList<Cast>(), result)
    }

    @Test
    fun `getMovieRecommendations - should return move recommendations when API delivers the goods`() =
        runTest {
            // Given
            val movieId = 123
            val page = 1
            val language = "en"

            val expectedCast =
                mockMovieSimilarsDto.movieSimilarDto?.map { it.toEntity() } ?: emptyList()

            // When
            coEvery {
                movieDetailsRemoteDataSource.getSimilarMovies(
                    movieId,
                    page,
                    language
                )
            } returns mockMovieSimilarsDto
            val result = movieRepository.getMovieRecommendations(movieId, page)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getMovieGallery - should return move gallery when API delivers the goods`() = runTest {
        // Given
        val movieId = 123

        val expectedCast = mockMovieImagesDto.logos?.map { it.toEntity(movieId) } ?: emptyList()

        // When
        coEvery {
            movieDetailsRemoteDataSource.getMovieImages(
                movieId
            )
        } returns mockMovieImagesDto

        coEvery { galleryLocalDataSource.addGallery(any()) } returns Unit

        val imageLocal =
            mockMovieImagesDto.logos?.map { it.toEntity(movieId) }?.map { it.toLocalDto() }
                ?: emptyList()
        coEvery {
            galleryLocalDataSource.getGalleryByMovieId(movieId)
        } returns GalleryEntity(
            images = imageLocal,
            id = 0,
            movieId = movieId
        )

        val result = movieRepository.getMovieGallery(movieId).images

        // Then
        assertEquals(expectedCast, result)
    }

    @Test
    fun `getMovieGallery - should return movie gallery from remote when local is empty`() =
        runTest {
            // Given
            val movieId = 550

            val expectedImages =
                mockMovieImagesDto.logos?.map { it.toEntity(movieId) } ?: emptyList()

            // When && Then
            coEvery {
                galleryLocalDataSource.getGalleryByMovieId(movieId)
            } returns null andThen GalleryEntity(
                images = expectedImages.map { it.toLocalDto() },
                id = 0,
                movieId = movieId
            )

            coEvery { movieDetailsRemoteDataSource.getMovieImages(movieId) } returns mockMovieImagesDto

            coEvery { galleryLocalDataSource.addGallery(any()) } returns Unit

            assertThrows<NoGalleryMovieDetailsFoundException> {
                movieRepository.getMovieGallery(movieId)
            }
        }

    @Test
    fun `getCompanyProducts - should return company products when API delivers the goods`() =
        runTest {
            // Given
            val movieId = 123
            val language = "en"

            val mockMovieImagesDto = listOf(MovieProductionCompanyDto(name = "sonic"))
            val expectedCast = mockMovieImagesDto.map { it.toEntity() }

            // When
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(
                    movieId, language
                ).productionCompanies
            } returns mockMovieImagesDto

            val result = movieRepository.getCompanyProducts(movieId)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getMovieReview - should return movie review when API delivers the goods`() =
        runTest {
            // Given
            val movieId = 123
            val language = "en"
            val page = 1


            val mockMovieReviewsDto =
                MovieReviewsDto(results = listOf(review.toEntity().toRemoteDto()))

            val expectedCast = mockMovieReviewsDto.results?.map { it.toEntity() }

            // When
            coEvery {
                movieDetailsRemoteDataSource.getMovieReviews(
                    movieId, page, language
                )
            } returns mockMovieReviewsDto

            coEvery { reviewLocalDataSource.addReview(any()) } returns Unit

            val reviewLocal =
                mockMovieReviewsDto.results?.map { it.toEntity().toLocalDto() } ?: emptyList()

            coEvery { reviewLocalDataSource.getReviewsForMovie(movieId) } returns reviewLocal

            val result = movieRepository.getMovieReview(movieId, page)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getMovieReview - should return movie review from remote when local is empty`() = runTest {
        // Given
        val movieId = 123
        val language = "en"
        val page = 1

        val mockMovieReviewsDto = MovieReviewsDto(results = listOf(review.toEntity().toRemoteDto()))

        // When
        coEvery {
            reviewLocalDataSource.getReviewsForMovie(movieId)
        } returns emptyList() andThen emptyList()

        coEvery {
            movieDetailsRemoteDataSource.getMovieReviews(
                movieId,
                page,
                language
            )
        } returns mockMovieReviewsDto

        coEvery { reviewLocalDataSource.addReview(any()) } returns Unit

        val result = movieRepository.getMovieReview(movieId, page)

        // Then
        coVerify { reviewLocalDataSource.addReview(any()) }
        assertEquals(emptyList<Review>(), result)
    }

}