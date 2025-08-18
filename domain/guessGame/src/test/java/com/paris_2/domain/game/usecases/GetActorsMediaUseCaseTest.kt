package com.paris_2.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.Actor
import com.paris_2.domain.game.entity.ActorMedia
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetActorsMediaUseCaseTest {

    private lateinit var getPopularActorsUseCase: GetPopularActorsUseCase
    private lateinit var getActorsMediaUseCase: GetActorsMediaUseCase

    @BeforeEach
    fun setUp() {
        getPopularActorsUseCase = mockk()
        getActorsMediaUseCase = GetActorsMediaUseCase(getPopularActorsUseCase)
    }

    @Test
    fun `should flatten media from popular actors`() = runTest {
        // Given
        coEvery { getPopularActorsUseCase() } returns sampleActors
        // When
        val result = getActorsMediaUseCase()
        // Then
        assertThat(result).containsExactlyElementsIn(sampleActors.flatMap { it.media })
    }

    @Test
    fun `should call getPopularActorsUseCase exactly once`() = runTest {
        // Given
        coEvery { getPopularActorsUseCase() } returns sampleActors
        // When
        getActorsMediaUseCase()
        // Then
        coVerify(exactly = 1) { getPopularActorsUseCase() }
    }

    @Test
    fun `should return empty list when no actors returned`() = runTest {
        // Given
        coEvery { getPopularActorsUseCase() } returns emptyList()
        // When
        val result = getActorsMediaUseCase()
        // Then
        assertThat(result).isEmpty()
    }

    private companion object {
        private val sampleActors = listOf(
            Actor(
                id = 1,
                name = "Actor One",
                imageUri = "/a1.jpg",
                media = listOf(
                    ActorMedia(
                        id = 10,
                        name = "Movie A",
                        posterImg = "/pA.jpg",
                        yearOfRelease = LocalDate(2020, 1, 1),
                        genres = listOf(12, 18)
                    ),
                    ActorMedia(
                        id = 11,
                        name = "Movie B",
                        posterImg = "/pB.jpg",
                        yearOfRelease = LocalDate(2021, 5, 15),
                        genres = listOf(28)
                    )
                )
            ),
            Actor(
                id = 2,
                name = "Actor Two",
                imageUri = "/a2.jpg",
                media = listOf(
                    ActorMedia(
                        id = 20,
                        name = "Movie C",
                        posterImg = "/pC.jpg",
                        yearOfRelease = LocalDate(2019, 7, 20),
                        genres = emptyList()
                    )
                )
            ),
            Actor(
                id = 3,
                name = "Actor Three",
                imageUri = "/a3.jpg",
                media = emptyList()
            )
        )
    }
}
