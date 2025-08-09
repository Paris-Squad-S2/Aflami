package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.GenreUserInteraction
import com.paris_2.domain.media.repository.GenresInteractionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IncrementCategoryInteractionUseCaseTest {
    private lateinit var genresInteractionRepository: GenresInteractionRepository
    private lateinit var incrementUseCase: IncrementCategoryInteractionUseCase

    @BeforeEach
    fun setUp() {
        genresInteractionRepository = mockk(relaxed = true)
        incrementUseCase = IncrementCategoryInteractionUseCase(genresInteractionRepository)
    }

    @Test
    fun `should call getCategoryInteractions when existing interaction is null`() =
        runTest {
            //Given
            val genre = Category.HISTORY
            coEvery { genresInteractionRepository.getCategoryInteractions(genre) } returns null

            //When
            incrementUseCase(listOf(genre))

            //Then
            coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genre) }
        }

    @Test
    fun `should upsert new interaction when existing interaction is null`() = runTest {
        //Given
        val genre = Category.HISTORY
        coEvery { genresInteractionRepository.getCategoryInteractions(genre) } returns null

        //When
        incrementUseCase(listOf(genre))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = genre, interactionCount = 1)
            )
        }
    }

    @Test
    fun `should call getCategoryInteractions when interaction exists`() = runTest {
        //Given
        val genre = Category.KIDS
        coEvery { genresInteractionRepository.getCategoryInteractions(genre) } returns 5

        //When
        incrementUseCase(listOf(genre))

        //Then
        coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genre) }

    }

    @Test
    fun `should upsert incremented interaction when existing count is present`() = runTest {
        //Given
        val genre = Category.KIDS
        coEvery { genresInteractionRepository.getCategoryInteractions(genre) } returns 5

        //When
        incrementUseCase(listOf(genre))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = genre, interactionCount = 6)
            )
        }
    }

    @Test
    fun `should increment interactions for multiple genres independently`() = runTest {
        //Given
        val genres = listOf(Category.ACTION, Category.ADVENTURE, Category.ANIMATION)
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.ACTION) } returns 2
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.ADVENTURE) } returns null
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.ANIMATION) } returns 7

        //When
        incrementUseCase(genres)

        //Then
        coVerify { genresInteractionRepository.getCategoryInteractions(Category.ACTION) }
        coVerify { genresInteractionRepository.getCategoryInteractions(Category.ADVENTURE) }
        coVerify { genresInteractionRepository.getCategoryInteractions(Category.ANIMATION) }


    }

    @Test
    fun `should upsert incremented interaction for genre 1`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.ACTION) } returns 2

        //When
        incrementUseCase(listOf(Category.ACTION))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = Category.ACTION, interactionCount = 3)
            )
        }
    }

    @Test
    fun `should upsert interaction for genre 2 when no existing interaction`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.ADVENTURE) } returns null

        //When
        incrementUseCase(listOf(Category.ADVENTURE))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = Category.ADVENTURE, interactionCount = 1)
            )
        }
    }

    @Test
    fun `should upsert incremented interaction for genre 3`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.ANIMATION) } returns 7

        //When
        incrementUseCase(listOf(Category.ANIMATION))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = Category.ANIMATION, interactionCount = 8)
            )
        }
    }

    @Test
    fun `should not call getCategoryInteractions when genres list is empty`() = runTest {
        incrementUseCase(emptyList())

        coVerify(exactly = 0) { genresInteractionRepository.getCategoryInteractions(any()) }
    }

    @Test
    fun `should not call upsertInteraction when genres list is empty`() = runTest {
        incrementUseCase(emptyList())

        coVerify(exactly = 0) { genresInteractionRepository.upsertInteraction(any()) }
    }

}

