package com.paris_2.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.testUtils.createMedia
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetTvShowsByCategoryUseCaseTest {

    private lateinit var mediaRepository: MediaRepository
    private lateinit var getTvShowsByCategoryUseCase: GetTvShowsByCategoryUseCase

    @BeforeEach
    fun setUp() {
        mediaRepository = mockk()
        getTvShowsByCategoryUseCase = GetTvShowsByCategoryUseCase(mediaRepository)
    }

    @Test
    fun `should return only TV shows for given category`() = runTest {
        // Given
        coEvery { mediaRepository.getTvShowsByCategory(genreId, page) } returns tvShowsList

        // When
        val result = getTvShowsByCategoryUseCase(genreId, page)

        // Then
        assertThat(result).hasSize(3)
    }

    @Test
    fun `should return correct TV show titles for given category`() = runTest {
        // Given
        coEvery { mediaRepository.getTvShowsByCategory(genreId, page) } returns tvShowsList

        // When
        val result = getTvShowsByCategoryUseCase(genreId, page)

        // Then
        assertThat(result.map { it.title }).containsExactly("Show 1", "Show 2", "Show 3")
    }

    @Test
    fun `should verify repository is called exactly once`() = runTest {
        // Given
        coEvery { mediaRepository.getTvShowsByCategory(genreId, page) } returns tvShowsList

        // When
        getTvShowsByCategoryUseCase(genreId, page)

        // Then
        coVerify(exactly = 1) { mediaRepository.getTvShowsByCategory(genreId, page) }
    }

    @Test
    fun `should return empty list when repository returns no TV shows`() = runTest {
        // Given
        coEvery { mediaRepository.getTvShowsByCategory(genreId, page) } returns emptyList()

        // When
        val result = getTvShowsByCategoryUseCase(genreId, page)

        // Then
        assertThat(result).isEmpty()
    }
    private companion object {
        const val genreId = 28
        const val page = 1
        val tvShowsList = listOf(
            createMedia(id = 1, title = "Show 1", type = MediaType.TvShow),
            createMedia(id = 2, title = "Show 2", type = MediaType.TvShow),
            createMedia(id = 3, title = "Show 3", type = MediaType.TvShow)
        )
    }

}