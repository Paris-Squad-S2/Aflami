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
    fun `should return only movies when repository returns mixed media`() = runTest {

        // Given
        val countryName = "United States"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns mixedMediaList

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertEquals(3, result.size)
    }

    @Test
    fun `should return correct movie titles`() = runTest {

        // Given
        val countryName = "United States"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns mixedMediaList

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertEquals(listOf("Movie 1", "Movie 2", "Movie 3"), result.map { it.title })
    }

    @Test
    fun `should filter only movies by type`() = runTest {

        // Given
        val countryName = "United States"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns mixedMediaList

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertTrue(result.all { it.type == MediaType.MOVIE })
    }

    @Test
    fun `verify repository should be called once for mixed media case`() = runTest {

        //Given
        val countryName = "United States"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns mixedMediaList

        //When
        getMoviesOnlyByCountryNameUseCase(countryName)

        //Then
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(countryName) }
    }

    @Test
    fun `should return empty list when only TV shows returned`() = runTest {
        //Given
        val countryName = "Canada"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns nonMovieMedia

        //When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `verify repository is called for only TV shows`() = runTest {

        //Given
        val countryName = "Canada"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns nonMovieMedia

        //When
        getMoviesOnlyByCountryNameUseCase(countryName)

        //Then
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(countryName) }
    }

    @Test
    fun `should return empty list when repository response is empty`() = runTest {

        // Given
        val countryName = "Canada"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns nonMovieMedia

        // When
        val result = getMoviesOnlyByCountryNameUseCase(countryName)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `verify repository is called when repository response is empty`() = runTest {

        //Given
        val countryName = "Egypt"
        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns emptyList()

        //When
        getMoviesOnlyByCountryNameUseCase(countryName)

        //Then
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(countryName) }
    }

    @Test
    fun `should return empty list when country name is empty`() = runTest {

        //Given
        val emptyCountryName = ""
        coEvery { searchMediaRepository.getMoviesByCountry(emptyCountryName) } returns emptyList()

        //When
        val result = getMoviesOnlyByCountryNameUseCase(emptyCountryName)

        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `verify repository is called when country name is empty`() = runTest {

        //Given
        val emptyCountryName = ""
        coEvery { searchMediaRepository.getMoviesByCountry(emptyCountryName) } returns emptyList()

        //When
        getMoviesOnlyByCountryNameUseCase(emptyCountryName)

        //Then
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(emptyCountryName) }
    }

    @Test
    fun `should return empty list when country name is whitespace`() = runTest {

        //Given
        val whitespaceCountryName = "   "
        coEvery { searchMediaRepository.getMoviesByCountry(whitespaceCountryName) } returns emptyList()

        //When
        val result = getMoviesOnlyByCountryNameUseCase(whitespaceCountryName)

        //Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `verify repository is called when country name is whitespace`() = runTest {

        //Given
        val whitespaceCountryName = "   "
        coEvery { searchMediaRepository.getMoviesByCountry(whitespaceCountryName) } returns emptyList()

        //When
        getMoviesOnlyByCountryNameUseCase(whitespaceCountryName)

        //Then
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(whitespaceCountryName) }
    }

    @Test
    fun `should return movie when country name is case-sensitive `() = runTest {

        // Given
        val upperCaseCountry = "CANADA"
        coEvery { searchMediaRepository.getMoviesByCountry(upperCaseCountry) } returns movies

        // When
        val result = getMoviesOnlyByCountryNameUseCase(upperCaseCountry)

        // Then
        assertThat(result).hasSize(1)
    }

    @Test
    fun `verify repository is called when country name is case-sensitive`() = runTest {

        //Given
        val upperCaseCountry = "CANADA"
        coEvery { searchMediaRepository.getMoviesByCountry(upperCaseCountry) } returns movies

        //When
        getMoviesOnlyByCountryNameUseCase(upperCaseCountry)

        //Then
        coVerify(exactly = 1) { searchMediaRepository.getMoviesByCountry(upperCaseCountry) }
    }

    companion object{
        val mixedMediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW),
            createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE),
            createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW),
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE)
        )
        val nonMovieMedia = listOf(
            createMedia(id = 1, title = "TV Show 1", type = MediaType.TVSHOW),
            createMedia(id = 2, title = "TV Show 2", type = MediaType.TVSHOW)
        )
        val movies = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE)
        )
    }
}
