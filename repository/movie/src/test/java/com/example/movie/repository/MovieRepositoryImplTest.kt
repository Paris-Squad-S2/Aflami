package com.example.movie.repository

import com.example.movie.dataSource.remote.MovieDetailsRemoteDataSource
import com.example.movie.mapper.toEntity
import com.example.movie.models.remote.MovieCastDto
import com.example.movie.models.remote.MovieCreditsDto
import com.example.movie.models.remote.MovieDto
import com.example.movie.models.remote.MovieImagesDto
import com.example.movie.models.remote.MovieLogoDto
import com.example.movie.models.remote.MovieProductionCompanyDto
import com.example.movie.models.remote.MovieReviewDto
import com.example.movie.models.remote.MovieReviewsDto
import com.example.movie.models.remote.MovieSimilarDto
import com.example.movie.models.remote.MovieSimilarsDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class MovieRepositoryImplTest {
    private val movieDetailsRemoteDataSource = mockk<MovieDetailsRemoteDataSource>()
    private val repository = MovieRepositoryImpl(movieDetailsRemoteDataSource)

    @Test
    fun `getMovieDetails - should return movie details when API delivers the goods`() = runTest {
        // Given
        val movieId = 550
        val language = "en"
        val mockMovieDto = MovieDto(
            id = movieId,
            title = "The Epic Blockbuster",
            overview = "Mind-blowing action sequences",
            releaseDate = "2024-07-15"
        )
        val expectedMovie = mockMovieDto.toEntity()

        coEvery {
            movieDetailsRemoteDataSource.getMovieDetails(
                movieId,
                language
            )
        } returns mockMovieDto
        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        assertEquals(expectedMovie.title, result.title)
    }

    @Test
    fun `getMovieCast - should return movie cast when API delivers the goods`() = runTest {
        // Given
        val movieId = 550
        val language = "en"
        val mockMovieCreditsDto = MovieCreditsDto(
            id = movieId,
            cast = listOf(
                MovieCastDto(
                    name = "alex"
                )
            )
        )
        val expectedMovieCast = mockMovieCreditsDto.cast?.map { it.toEntity() } ?: emptyList()

        // When
        coEvery {
            movieDetailsRemoteDataSource.getMovieCredits(
                movieId,
                language
            )
        } returns mockMovieCreditsDto
        val result = repository.getMovieCast(movieId)

        // Then
        assertEquals(expectedMovieCast, result)

    }

    @Test
    fun `getMovieRecommendations - should return move recommendations when API delivers the goods`() =
        runTest {
            // Given
            val movieId = 123
            val page = 1
            val language = "en"

            val mockMovieSimilarsDto = MovieSimilarsDto(
                movieSimilarDto = listOf(
                    MovieSimilarDto(
                        title = "batman"
                    )
                )
            )
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
            val result = repository.getMovieRecommendations(movieId, page)

            // Then
            assertEquals(expectedCast, result)
        }

    @Test
    fun `getMovieGallery - should return move gallery when API delivers the goods`() = runTest {
        // Given
        val movieId = 123

        val mockMovieImagesDto = MovieImagesDto(
            id = movieId,
            logos = listOf(
                MovieLogoDto(filePath = "http://images.jpeg")
            )
        )
        val expectedCast = mockMovieImagesDto.logos?.map { it.toEntity(movieId) } ?: emptyList()

        // When
        coEvery {
            movieDetailsRemoteDataSource.getMovieImages(
                movieId
            )
        } returns mockMovieImagesDto
        val result = repository.getMovieGallery(movieId).images

        // Then
        assertEquals(expectedCast, result)
    }

    @Test
    fun `getCompanyProducts - should return company products when API delivers the goods`() =
        runTest {
            // Given
            val movieId = 123
            val language = "en"

            val mockMovieImagesDto = listOf(
                MovieProductionCompanyDto(
                    name = "sonic"
                )
            )

            val expectedCast = mockMovieImagesDto.map { it.toEntity() }

            // When
            coEvery {
                movieDetailsRemoteDataSource.getMovieDetails(
                    movieId, language
                ).productionCompanies
            } returns mockMovieImagesDto

            val result = repository.getCompanyProducts(movieId)

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

            val mockMovieReviewsDto = MovieReviewsDto(
                results = listOf(
                    MovieReviewDto(
                        author = "mohammed"
                    )
                )
            )

            val expectedCast = mockMovieReviewsDto.results?.map { it.toEntity() }

            // When
            coEvery {
                movieDetailsRemoteDataSource.getMovieReviews(
                    movieId, page, language
                )
            } returns mockMovieReviewsDto

            val result = repository.getMovieReview(movieId,page)

            // Then
            assertEquals(expectedCast, result)
        }

}