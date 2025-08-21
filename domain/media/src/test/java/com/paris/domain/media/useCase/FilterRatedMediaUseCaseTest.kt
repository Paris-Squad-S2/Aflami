package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.MediaRepository
import com.paris.domain.media.testUtils.createMedia
import com.paris.domain.media.useCase.media.FilterRatedMediaUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FilterRatedMediaUseCaseTest {

    private lateinit var mediaRepository: MediaRepository
    private lateinit var filterRatedMediaUseCase: FilterRatedMediaUseCase

    private val accountId = 1

    @BeforeEach
    fun setUp() {
        mediaRepository = mockk()
        filterRatedMediaUseCase = FilterRatedMediaUseCase(mediaRepository)
    }

    @Test
    fun `should return only movies when MediaType is MOVIE`() = runTest {
        // Given
        coEvery { mediaRepository.getRatedMedia(accountId) } returns mediaList

        // When
        val result = filterRatedMediaUseCase(accountId,MediaType.Movie)

        // Then
        val expected = listOf(mediaList[0])
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return only TV shows when MediaType is TVSHOW`() = runTest {
        // Given
        coEvery { mediaRepository.getRatedMedia(accountId) } returns mediaList

        // When
        val result = filterRatedMediaUseCase(accountId,MediaType.TvShow)

        // Then
        val expected = listOf(mediaList[1], mediaList[2])
        assertThat(result).isEqualTo(expected)
    }


    @Test
    fun `should return empty list when no media matches the type`() = runTest {
        // Given
        val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie)
        )
        coEvery { mediaRepository.getRatedMedia(accountId) } returns mediaList

        // When
        val result = filterRatedMediaUseCase(accountId,MediaType.TvShow)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when media list is empty`() = runTest {
        // Given
        coEvery { mediaRepository.getRatedMedia(accountId) } returns emptyList()

        // When
        val result = filterRatedMediaUseCase(accountId,MediaType.Movie)

        // Then
        assertThat(result).isEmpty()
    }

    companion object {
        private val mediaList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie),
            createMedia(id = 2, title = "Series 1", type = MediaType.TvShow),
            createMedia(id = 3, title = "Series 2", type = MediaType.TvShow)
        )
    }

}