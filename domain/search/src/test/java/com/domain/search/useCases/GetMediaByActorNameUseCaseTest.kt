package com.domain.search.useCases

import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testUtils.createMedia

class GetMediaByActorNameUseCaseTest {
    private lateinit var searchMediaRepository: SearchMediaRepository
    private lateinit var getMediaByActorNameUseCase: GetMediaByActorNameUseCase

    @BeforeEach
    fun setUp() {
        searchMediaRepository = mockk()
        getMediaByActorNameUseCase = GetMediaByActorNameUseCase(searchMediaRepository)
    }

    @Test
    fun `should return only movies for given actor`() = runTest {
        // Given
        val actorName = "actor"
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW),
            createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE),
            createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW),
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE)
        )
        coEvery { searchMediaRepository.getMediaByActor(actorName) } returns mediaList

        // When
        val result = getMediaByActorNameUseCase(actorName)

        // Then
        assertEquals(3, result.size)
    }

    @Test
    fun `should return correct movie titles for given actor`() = runTest {

        // Given
        val actorName = "actor"
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW),
            createMedia(id = 3, title = "Movie 2", type = MediaType.MOVIE),
            createMedia(id = 4, title = "Series 2", type = MediaType.TVSHOW),
            createMedia(id = 5, title = "Movie 3", type = MediaType.MOVIE)
        )
        coEvery { searchMediaRepository.getMediaByActor(actorName) } returns mediaList

        // When
        val result = getMediaByActorNameUseCase(actorName)

        // Then
        assertEquals(listOf("Movie 1", "Movie 2", "Movie 3"), result.map { it.title })
    }

    @Test
    fun `should verify repository is called exactly once for given actor`() = runTest {

        // Given
        val actorName = "actor"
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE)
        )
        coEvery { searchMediaRepository.getMediaByActor(actorName) } returns mediaList

        // When
        getMediaByActorNameUseCase(actorName)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByActor(actorName) }
    }

    @Test
    fun `should return empty list when repository returns no media for given actor`() = runTest {

            // Given
            val actorName = "Wael"
            coEvery { searchMediaRepository.getMediaByActor(actorName) } returns emptyList()

            // When
            val result = getMediaByActorNameUseCase.invoke(actorName)

            // Then
            assertTrue(result.isEmpty())

        }

    @Test
    fun `should verify repository is called when no media returned`() = runTest {
        // Given
        val actorName = "Wael"
        coEvery { searchMediaRepository.getMediaByActor(actorName) } returns emptyList()

        // When
        getMediaByActorNameUseCase(actorName)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByActor(actorName) }
    }

    @Test
    fun `should return empty list when only TV shows are returned`() = runTest {
        // Given
        val actorName = "actor"
        val tvOnlyMedia = listOf(
            createMedia(id = 10, title = "Show 1", type = MediaType.TVSHOW),
            createMedia(id = 11, title = "Show 2", type = MediaType.TVSHOW)
        )
        coEvery { searchMediaRepository.getMediaByActor(actorName) } returns tvOnlyMedia

        // When
        val result = getMediaByActorNameUseCase(actorName)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should verify repository is called when only TV shows are returned`() = runTest {

        // Given
        val actorName = "actor"
        val tvOnlyMedia = listOf(
            createMedia(id = 10, title = "Show 1", type = MediaType.TVSHOW),
            createMedia(id = 11, title = "Show 2", type = MediaType.TVSHOW)
        )
        coEvery { searchMediaRepository.getMediaByActor(actorName) } returns tvOnlyMedia

        // When
        getMediaByActorNameUseCase(actorName)

        // Then
        coVerify(exactly = 1) { searchMediaRepository.getMediaByActor(actorName) }
    }

}
