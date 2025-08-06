package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.repository.MediaRepository
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.testUtils.createMedia
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterRatedMediaUseCaseTest {

    private lateinit var mediaRepository: MediaRepository
    private lateinit var filterRatedMediaUseCase: FilterRatedMediaUseCase

    @BeforeEach
    fun setUp() {
        mediaRepository = mockk()
        filterRatedMediaUseCase = FilterRatedMediaUseCase(mediaRepository)
    }

    @Test
    fun `should return only movies when MediaType is MOVIE`() = runTest {
        // Given
        coEvery { mediaRepository.getRatedMedia() } returns mediaList

        // When
        val result = filterRatedMediaUseCase(MediaType.MOVIE)

        // Then
        val expected = listOf(mediaList[0])
        assertEquals(expected, result)
    }

    @Test
    fun `should return only TV shows when MediaType is TVSHOW`() = runTest {
        // Given
        coEvery { mediaRepository.getRatedMedia() } returns mediaList

        // When
        val result = filterRatedMediaUseCase(MediaType.TVSHOW)

        // Then
        val expected = listOf(mediaList[1], mediaList[2])
        assertEquals(expected, result)
    }


    @Test
    fun `should return empty list when no media matches the type`() = runTest {
        // Given
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE)
        )
        coEvery { mediaRepository.getRatedMedia() } returns mediaList

        // When
        val result = filterRatedMediaUseCase(MediaType.TVSHOW)

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should return empty list when media list is empty`() = runTest {
        // Given
        coEvery { mediaRepository.getRatedMedia() } returns emptyList()

        // When
        val result = filterRatedMediaUseCase(MediaType.MOVIE)

        // Then
        assertEquals(emptyList(), result)
    }

    companion object {
        private val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.MOVIE),
            createMedia(id = 2, title = "Series 1", type = MediaType.TVSHOW),
            createMedia(id = 3, title = "Series 2", type = MediaType.TVSHOW)
        )
    }

}