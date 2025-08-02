package com.paris_2.domain.media.useCase


import com.domain.media.entity.GenreUserInteraction
import com.domain.media.repository.GenresInteractionRepository
import com.domain.media.testUtils.assertIds
import com.domain.media.testUtils.media
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class SortingMediaByCategoriesInteractionUseCaseTest {
    private lateinit var genresInteractionRepository: GenresInteractionRepository
    private lateinit var sortingUseCase: SortingMediaByCategoriesInteractionUseCase

    @BeforeEach
    fun setUp() {
        genresInteractionRepository = mockk(relaxed = true)
        sortingUseCase = SortingMediaByCategoriesInteractionUseCase(genresInteractionRepository)
    }

    @Test
    fun `should sort media by sum of category interaction counts descending`() = runTest {

        // Given
        coEvery { genresInteractionRepository.getAllInteractions() } returns listOf(
            GenreUserInteraction(1, 10),
            GenreUserInteraction(2, 5),
            GenreUserInteraction(3, 1)
        )

        val mediaList = listOf(
            media(101, "A", listOf(1, 3)),
            media(102, "B", listOf(2)),
            media(103, "C", listOf(3)),
            media(104, "D", listOf(2, 3))
        )

        // When
        val result = sortingUseCase(mediaList)

        // Then
        assertIds(result, 101, 104, 102, 103)
    }

    @Test
    fun `should preserve input order when media have equal category interaction sums`() = runTest {
        // Given
        coEvery { genresInteractionRepository.getAllInteractions() } returns listOf(
            GenreUserInteraction(1, 10),
            GenreUserInteraction(2, 0)
        )

        val mediaList = listOf(
            media(200, "A", listOf(1)),
            media(201, "B", listOf(2))
        )

        // When
        val result = sortingUseCase(mediaList)

        // Then
        assertIds(result, 200, 201)
    }

    @Test
    fun `should order medias with no matching genres as zero interaction`() = runTest {
        // Given
        coEvery { genresInteractionRepository.getAllInteractions() } returns listOf(
            GenreUserInteraction(1, 7)
        )

        val mediaList = listOf(
            media(300, "A", listOf(2)), // No matching genre
            media(301, "B", listOf(1))  // Matching genre
        )

        // When
        val result = sortingUseCase(mediaList)

        // Then
        assertIds(result, 301, 300)
    }

    @Test
    fun `should return empty list when input media list is empty`() = runTest {
        // Given
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()

        // When
        val result = sortingUseCase(emptyList())

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun `should treat all as zero when repository returns no interactions`() = runTest {
        // Given
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()

        val mediaList = listOf(
            media(401, "A", listOf(2)),
            media(402, "B", listOf(5))
        )

        // When
        val result = sortingUseCase(mediaList)

        // Then
        assertIds(result, 401, 402)
    }

    @Test
    fun `should call genresInteractionRepository to retrieve interactions`() = runTest {
        // Given
        val mediaList = listOf(media(500, "A", listOf(1)))
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()

        // When
        sortingUseCase(mediaList)

        // Then
        coVerify(exactly = 1) { genresInteractionRepository.getAllInteractions() }
    }

}
