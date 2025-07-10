package com.domain.search.useCases

import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testUtils.createMedia

class GetMoviesOnlyByCountryNameUseCaseTest {

    private lateinit var searchMediaRepository: SearchMediaRepository
    private lateinit var useCase: GetMoviesOnlyByCountryNameUseCase

    @BeforeEach
    fun setUp() {
        searchMediaRepository = mockk()
        useCase = GetMoviesOnlyByCountryNameUseCase(searchMediaRepository)
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
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE)
        )

        coEvery { searchMediaRepository.getMoviesByCountry(countryName) } returns mixedMediaList

        // When
        val result = useCase.invoke(countryName)

        // Then
        assertEquals(3, result.size)
        assertTrue(result.all { it.type == MediaType.MOVIE })
        assertEquals(listOf("Movie 1", "Movie 2", "Movie 3"), result.map { it.title })
    }
}
