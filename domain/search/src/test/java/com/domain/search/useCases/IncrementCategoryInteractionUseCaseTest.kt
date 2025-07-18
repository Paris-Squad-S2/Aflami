package com.domain.search.useCases

import com.domain.search.model.GenreUserInteractionModel
import com.domain.search.repository.GenresInteractionRepository
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
    fun `should increment interaction for single genreId when existing interaction is null`() =
        runTest {
            val genreId = 10
            coEvery { genresInteractionRepository.getCategoryInteractions(genreId) } returns null

            incrementUseCase(listOf(genreId))

            coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genreId) }
            coVerify {
                genresInteractionRepository.upsertInteraction(
                    GenreUserInteractionModel(genreId = genreId, interactionCount = 1)
                )
            }
        }

    @Test
    fun `should increment interaction for single genreId with existing count`() = runTest {
        val genreId = 42
        coEvery { genresInteractionRepository.getCategoryInteractions(genreId) } returns 5

        incrementUseCase(listOf(genreId))

        coVerify(exactly = 1) { genresInteractionRepository.getCategoryInteractions(genreId) }
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteractionModel(genreId = genreId, interactionCount = 6)
            )
        }
    }

    @Test
    fun `should increment interactions for multiple genreIds independently`() = runTest {
        val genreIds = listOf(1, 2, 3)
        coEvery { genresInteractionRepository.getCategoryInteractions(1) } returns 2
        coEvery { genresInteractionRepository.getCategoryInteractions(2) } returns null
        coEvery { genresInteractionRepository.getCategoryInteractions(3) } returns 7

        incrementUseCase(genreIds)

        coVerify { genresInteractionRepository.getCategoryInteractions(1) }
        coVerify { genresInteractionRepository.getCategoryInteractions(2) }
        coVerify { genresInteractionRepository.getCategoryInteractions(3) }
        coVerify {
            genresInteractionRepository.upsertInteraction(
                GenreUserInteractionModel(genreId = 1, interactionCount = 3)
            )
            genresInteractionRepository.upsertInteraction(
                GenreUserInteractionModel(genreId = 2, interactionCount = 1)
            )
            genresInteractionRepository.upsertInteraction(
                GenreUserInteractionModel(genreId = 3, interactionCount = 8)
            )
        }
    }

    @Test
    fun `should do nothing when genreIds list is empty`() = runTest {
        incrementUseCase(emptyList())
        coVerify(exactly = 0) { genresInteractionRepository.getCategoryInteractions(any()) }
        coVerify(exactly = 0) { genresInteractionRepository.upsertInteraction(any()) }
    }
}
