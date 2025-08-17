package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.testUtils.createMedia
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class FilterWatchHistoryUseCaseTest {
    private lateinit var mediaRepository: MediaRepository
    private lateinit var filterWatchHistoryUseCase: FilterWatchHistoryUseCase

    @BeforeEach
    fun setUp() {
        mediaRepository = mockk()
        filterWatchHistoryUseCase = FilterWatchHistoryUseCase(mediaRepository)
    }

    @Test
    fun `should return only movies when MediaType is MOVIE`() = runTest {
        // Given
        coEvery { mediaRepository.getContinueWatchingMedia() } returns flowOf(mediaList)

        // When
        val result = filterWatchHistoryUseCase(MediaType.Movie).toList().flatten()

        // Then
        val expected = listOf(mediaList[0])
        assertEquals(expected, result)
    }

    @Test
    fun `should return only TV shows when MediaType is TVSHOW`() = runTest {
        // Given
        coEvery { mediaRepository.getContinueWatchingMedia() } returns flowOf(mediaList)

        // When
        val result = filterWatchHistoryUseCase(MediaType.TvShow).toList().flatten()

        // Then
        val expected = listOf(mediaList[1], mediaList[2])
        assertEquals(expected, result)
    }

    @Test
    fun `should return empty list when no media matches the type`() = runTest {
        // Given
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie)
        )
        coEvery { mediaRepository.getContinueWatchingMedia() } returns flowOf(mediaList)

        // When
        val result = filterWatchHistoryUseCase(MediaType.TvShow).toList().flatten()

        // Then
        assertEquals(emptyList<Media>(), result)
    }

    @Test
    fun `should return empty list when media list is empty`() = runTest {
        // Given
        coEvery { mediaRepository.getContinueWatchingMedia() } returns flowOf(emptyList())

        // When
        val result = filterWatchHistoryUseCase(MediaType.Movie).toList().flatten()

        // Then
        assertEquals(emptyList<Media>(), result)
    }


    companion object{
        private val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie),
            createMedia(id = 2, title = "Series 1", type = MediaType.TvShow),
            createMedia(id = 3, title = "Series 2", type = MediaType.TvShow)
        )
    }
}