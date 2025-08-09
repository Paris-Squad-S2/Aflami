package com.paris_2.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.SearchMediaRepository
import com.paris_2.domain.media.testUtils.createMedia
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        coEvery { searchMediaRepository.getMediaByQuery(query, page) } returns mediaList

        //When
        val result = searchByQueryUseCase(query, page)

        //Then
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun `should return exact media list when media matches query`() = runTest {

        // Given
        coEvery { searchMediaRepository.getMediaByQuery(query, page) } returns mediaList

        // When
        val result = searchByQueryUseCase(query, page)

        // Then
        assertThat(result).isEqualTo(mediaList)
    }

    @Test
    fun `should verify repository called once for matching query`() = runTest {

        // Given
        coEvery { searchMediaRepository.getMediaByQuery(query, page) } returns mediaList

        // When
        searchByQueryUseCase(query, page)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByQuery(query, page) }
    }

    @Test
    fun `should return empty list when no media matches query`() = runTest {

        //Given
        coEvery {
            searchMediaRepository.getMediaByQuery(
                unknownTitleQuery,
                page
            )
        } returns emptyList()

        //When
        val result = searchByQueryUseCase(unknownTitleQuery, page)

        //Then
        assertThat(result).isEmpty()


    }

    @Test
    fun `should verify repository is called once for unmatched query`() = runTest {
        // Given
        coEvery {
            searchMediaRepository.getMediaByQuery(
                unknownTitleQuery,
                page
            )
        } returns emptyList()

        // When
        searchByQueryUseCase(unknownTitleQuery, page)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByQuery(unknownTitleQuery, page) }
    }

    private companion object {
        val query = "chance"
        val unknownTitleQuery = "unknown title"
        val page = 1
        val mediaList = listOf(
            createMedia(id = 1, title = "chance Movie", type = MediaType.MOVIE),
            createMedia(id = 2, title = "chance Drama", type = MediaType.TVSHOW),
        )
    }
}