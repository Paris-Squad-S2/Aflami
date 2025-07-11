package com.domain.search.useCases

import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testUtils.createMedia

class GetMoviesOnlyByCountryNameUseCaseTest {

    private lateinit var searchMediaRepository: SearchMediaRepository
    private lateinit var getMoviesOnlyByCountryNameUseCase: GetMoviesOnlyByCountryNameUseCase

    @BeforeEach
    fun setUp() {
        searchMediaRepository = mockk()
        getMoviesOnlyByCountryNameUseCase = GetMoviesOnlyByCountryNameUseCase(searchMediaRepository)
    }

    @Test
    fun `invoke should Return OnlyMovies when Repository Returns Mixed Media Types`() = runTest {
        // Given
        val countryName = "United States"
        val mixedMediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW),
            createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE),
            createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW),
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE,)
        )

        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns mixedMediaList

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertEquals(3, result.size)
        assertTrue(result.all { it.type == MediaType.MOVIE })
        assertEquals(listOf("Movie 1", "Movie 2", "Movie 3"), result.map { it.title })
    }

    @Test
    fun `should return empty list when no movies found`() = runTest {
        // Given
        val countryName = "Canada"
        val nonMovieMedia = listOf(
            createMedia(id = 1, title = "TV Show 1", type = MediaType.TVSHOW),
            createMedia(id = 2, title = "TV Show 2", type = MediaType.TVSHOW)
        )
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns nonMovieMedia

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(countryName) }
    }

    @Test
    fun `should handle empty repository response`() = runTest {
        // Given
        val countryName = "Egypt"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns emptyList()

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(countryName) }
    }

    @Test
    fun `should handle empty country name`() = runTest {
        // Given
        val emptyCountryName = ""
        coEvery { searchMediaRepository.getMoviesByCountry(emptyCountryName) } returns emptyList()

        // When
        val result = getMoviesOnlyByCountryNameUseCase(emptyCountryName)

        // Then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(emptyCountryName) }
    }

    @Test
    fun `should handle whitespace-only country name`() = runTest {
        // Given
        val whitespaceCountryName = "   "
        coEvery { searchMediaRepository.getMoviesByCountry(whitespaceCountryName) } returns emptyList()

        // When
        val result = getMoviesOnlyByCountryNameUseCase(whitespaceCountryName)

        // Then
        assertThat(result).isEmpty()
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(whitespaceCountryName) }
    }

    @Test
    fun `should handle case-sensitive country names`() = runTest {
        // Given
        val upperCaseCountry = "CANADA"
        val movies = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE)
        )
        coEvery { searchMediaRepository.getMoviesByCountry(upperCaseCountry) } returns movies

        // When
        val result = getMoviesOnlyByCountryNameUseCase(upperCaseCountry)

        // Then
        assertThat(result).hasSize(1)
        assertThat(result.first().title).isEqualTo("Movie 1")
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(upperCaseCountry) }
    }
}
