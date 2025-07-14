package com.domain.search.useCases

import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import testUtils.createMedia
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchByQueryUseCaseTest {

    private lateinit var searchMediaRepository: SearchMediaRepository
    private lateinit var searchByQueryUseCase: SearchByQueryUseCase

    @BeforeEach
    fun setUp() {
        searchMediaRepository = mockk()
        searchByQueryUseCase = SearchByQueryUseCase(searchMediaRepository)
    }

    @Test
    fun `should return correct size when media matches query`() = runTest {

        //Given
        val query = "chance"
        coEvery { searchMediaRepository.getMediaByQuery(query) } returns mediaList

        //When
        val result = searchByQueryUseCase(query)

        //Then
        assertEquals(2, result.size)
    }

    @Test
    fun `should return exact media list when media matches query`() = runTest {

        // Given
        val query = "chance"
        coEvery { searchMediaRepository.getMediaByQuery(query) } returns mediaList

        // When
        val result = searchByQueryUseCase(query)

        // Then
        assertEquals(mediaList, result)
    }

    @Test
    fun `should verify repository called once for matching query`() = runTest {

        // Given
        val query = "chance"
        coEvery { searchMediaRepository.getMediaByQuery(query) } returns mediaList

        // When
        searchByQueryUseCase(query)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByQuery(query) }
    }

    @Test
    fun `should return empty list when no media matches query`() = runTest {

        //Given
        val query = "unknown title"
        coEvery { searchMediaRepository.getMediaByQuery(query) } returns emptyList()

        //When
        val result = searchByQueryUseCase(query)

        //Then
        assertTrue { result.isEmpty() }

    }

    @Test
    fun `should verify repository is called once for unmatched query`() = runTest {
        // Given
        val query = "unknown title"
        coEvery { searchMediaRepository.getMediaByQuery(query) } returns emptyList()

        // When
        searchByQueryUseCase(query)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByQuery(query) }
    }

    companion object {
        val mediaList = listOf(
            createMedia(id = 1, title = "chance Movie", type = MediaType.MOVIE),
            createMedia(id = 2, title = "chance Drama", type = MediaType.TVSHOW),
        )
    }
}