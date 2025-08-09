package com.paris_2.domain.media.useCase


import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.GenreUserInteraction
import com.paris_2.domain.media.repository.GenresInteractionRepository
import com.paris_2.domain.media.testUtils.assertIds
import com.paris_2.domain.media.testUtils.media
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


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
            GenreUserInteraction(Category.ACTION, 10),
            GenreUserInteraction(Category.ADVENTURE, 5),
            GenreUserInteraction(Category.ANIMATION, 1)
        )

        val mediaList = listOf(
            media(101, "A", listOf(Category.ACTION, Category.ANIMATION)),
            media(102, "B", listOf(Category.ADVENTURE)),
            media(103, "C", listOf(Category.ANIMATION)),
            media(104, "D", listOf(Category.ADVENTURE, Category.ANIMATION))
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
            GenreUserInteraction(Category.ACTION, 10),
            GenreUserInteraction(Category.ADVENTURE, 0)
        )

        val mediaList = listOf(
            media(200, "A", listOf(Category.ACTION)),
            media(201, "B", listOf(Category.ADVENTURE))
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
            GenreUserInteraction(Category.ACTION, 7)
        )

        val mediaList = listOf(
            media(300, "A", listOf(Category.ADVENTURE)), // No matching genre
            media(301, "B", listOf(Category.ACTION))  // Matching genre
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
        assertThat(result).isEmpty()
    }

    @Test
    fun `should treat all as zero when repository returns no interactions`() = runTest {
        // Given
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()

        val mediaList = listOf(
            media(401, "A", listOf(Category.ADVENTURE)),
            media(402, "B", listOf(Category.CRIME))
        )

        // When
        val result = sortingUseCase(mediaList)

        // Then
        assertIds(result, 401, 402)
    }

    @Test
    fun `should call genresInteractionRepository to retrieve interactions`() = runTest {
        // Given
        val mediaList = listOf(media(500, "A", listOf(Category.ACTION)))
        coEvery { genresInteractionRepository.getAllInteractions() } returns emptyList()

        // When
        sortingUseCase(mediaList)

        // Then
        coVerify(exactly = 1) { genresInteractionRepository.getAllInteractions() }
    }

}
