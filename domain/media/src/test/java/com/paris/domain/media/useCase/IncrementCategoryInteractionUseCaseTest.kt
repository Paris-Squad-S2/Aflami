package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.GenreUserInteraction
import com.paris.domain.media.repository.GenresInteractionRepository
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
            val genre = Category.History
            coEvery { genresInteractionRepository.getCategoryInteractions(genre) } returns null

            //When
            incrementUseCase(listOf(genre))

            //Then
            coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genre) }
        }

    @Test
    fun `should upsert new interaction when existing interaction is null`() = runTest {
        //Given
        val genre = Category.History
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
        val genre = Category.Kids
        coEvery { genresInteractionRepository.getCategoryInteractions(genre) } returns 5

        //When
        incrementUseCase(listOf(genre))

        //Then
        coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genre) }

    }

    @Test
    fun `should upsert incremented interaction when existing count is present`() = runTest {
        //Given
        val genre = Category.Kids
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
        val genres = listOf(Category.Action, Category.Adventure, Category.Animation)
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.Action) } returns 2
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.Adventure) } returns null
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.Animation) } returns 7

        //When
        incrementUseCase(genres)

        //Then
        coVerify { genresInteractionRepository.getCategoryInteractions(Category.Action) }
        coVerify { genresInteractionRepository.getCategoryInteractions(Category.Adventure) }
        coVerify { genresInteractionRepository.getCategoryInteractions(Category.Animation) }


    }

    @Test
    fun `should upsert incremented interaction for genre 1`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.Action) } returns 2

        //When
        incrementUseCase(listOf(Category.Action))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = Category.Action, interactionCount = 3)
            )
        }
    }

    @Test
    fun `should upsert interaction for genre 2 when no existing interaction`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.Adventure) } returns null

        //When
        incrementUseCase(listOf(Category.Adventure))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = Category.Adventure, interactionCount = 1)
            )
        }
    }

    @Test
    fun `should upsert incremented interaction for genre 3`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(Category.Animation) } returns 7

        //When
        incrementUseCase(listOf(Category.Animation))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(category = Category.Animation, interactionCount = 8)
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

