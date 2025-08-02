package com.domain.media.useCase

import com.domain.media.model.GenreUserInteraction
import com.domain.media.repository.GenresInteractionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

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
            val genreId = 10
            coEvery { genresInteractionRepository.getCategoryInteractions(genreId) } returns null

            //When
            incrementUseCase(listOf(genreId))

            //Then
            coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genreId) }
        }

    @Test
    fun `should upsert new interaction when existing interaction is null`() = runTest {
        //Given
        val genreId = 10
        coEvery { genresInteractionRepository.getCategoryInteractions(genreId) } returns null

        //When
        incrementUseCase(listOf(genreId))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genreId = genreId, interactionCount = 1)
            )
        }
    }

    @Test
    fun `should call getCategoryInteractions when interaction exists`() = runTest {
        //Given
        val genreId = 42
        coEvery { genresInteractionRepository.getCategoryInteractions(genreId) } returns 5

        //When
        incrementUseCase(listOf(genreId))

        //Then
        coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genreId) }

    }

    @Test
    fun `should upsert incremented interaction when existing count is present`() = runTest {
        //Given
        val genreId = 42
        coEvery { genresInteractionRepository.getCategoryInteractions(genreId) } returns 5

        //When
        incrementUseCase(listOf(genreId))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genreId = genreId, interactionCount = 6)
            )
        }
    }

    @Test
    fun `should increment interactions for multiple genreIds independently`() = runTest {
        //Given
        val genreIds = listOf(1, 2, 3)
        coEvery { genresInteractionRepository.getCategoryInteractions(1) } returns 2
        coEvery { genresInteractionRepository.getCategoryInteractions(2) } returns null
        coEvery { genresInteractionRepository.getCategoryInteractions(3) } returns 7

        //When
        incrementUseCase(genreIds)

        //Then
        coVerify { genresInteractionRepository.getCategoryInteractions(1) }
        coVerify { genresInteractionRepository.getCategoryInteractions(2) }
        coVerify { genresInteractionRepository.getCategoryInteractions(3) }


    }

    @Test
    fun `should upsert incremented interaction for genreId 1`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(1) } returns 2

        //When
        incrementUseCase(listOf(1))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genreId = 1, interactionCount = 3)
            )
        }
    }

    @Test
    fun `should upsert interaction for genreId 2 when no existing interaction`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(2) } returns null

        //When
        incrementUseCase(listOf(2))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genreId = 2, interactionCount = 1)
            )
        }
    }

    @Test
    fun `should upsert incremented interaction for genreId 3`() = runTest {
        //Given
        coEvery { genresInteractionRepository.getCategoryInteractions(3) } returns 7

        //When
        incrementUseCase(listOf(3))

        //Then
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteraction(genreId = 3, interactionCount = 8)
            )
        }
    }

    @Test
    fun `should not call getCategoryInteractions when genreIds list is empty`() = runTest {
        incrementUseCase(emptyList())

        coVerify(exactly = 0) { genresInteractionRepository.getCategoryInteractions(any()) }
    }

    @Test
    fun `should not call upsertInteraction when genreIds list is empty`() = runTest {
        incrementUseCase(emptyList())

        coVerify(exactly = 0) { genresInteractionRepository.upsertInteraction(any()) }
    }

}

