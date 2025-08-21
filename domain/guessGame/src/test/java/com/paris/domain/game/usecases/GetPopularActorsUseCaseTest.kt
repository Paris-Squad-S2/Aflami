package com.paris.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris.domain.game.entity.Actor
import com.paris.domain.game.entity.ActorMedia
import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.domain.media.entity.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPopularActorsUseCaseTest {

    private lateinit var repository: ActorPopularityRepository
    private lateinit var useCase: GetPopularActorsUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetPopularActorsUseCase(repository)
    }

    @Test
    fun `should return correct number of actors from repository`() = runTest {
        // Given
        coEvery { repository.getPopularActor() } returns sampleActors
        // When
        val result = useCase()
        // Then
        assertThat(result).hasSize(sampleActors.size)
    }

    @Test
    fun `should call getPopularActor exactly once`() = runTest {
        // Given
        coEvery { repository.getPopularActor() } returns sampleActors
        // When
        useCase()
        // Then
        coVerify(exactly = 1) { repository.getPopularActor() }
    }

    @Test
    fun `should return expected actors from repository`() = runTest {
        // Given
        coEvery { repository.getPopularActor() } returns sampleActors
        // When
        val result = useCase()
        // Then
        assertThat(result).isEqualTo(sampleActors)
    }

    @Test
    fun `should return empty list when repository returns no actors`() = runTest {
        // Given
        coEvery { repository.getPopularActor() } returns emptyList()
        // When
        val result = useCase()
        // Then
        assertThat(result).isEmpty()
    }

    private companion object {
        private val sampleActors = listOf(
            Actor(
                id = 1,
                name = "Actor One",
                imageUri = "/path1.jpg",
                media = listOf(
                    ActorMedia(
                        id = 100,
                        name = "Movie A",
                        posterImg = "/posterA.jpg",
                        yearOfRelease = LocalDate(2020, 1, 1),
                        genres = listOf(Category.ScifiFantasy, Category.ActionAdventure)
                    )
                )
            ),
            Actor(
                id = 2,
                name = "Actor Two",
                imageUri = "/path2.jpg",
                media = listOf(
                    ActorMedia(
                        id = 200,
                        name = "Movie B",
                        posterImg = "/posterB.jpg",
                        yearOfRelease = LocalDate(2021, 5, 15),
                        genres = listOf(Category.WarPolitics)
                    )
                )
            ),
            Actor(
                id = 3,
                name = "Actor Three",
                imageUri = "/path3.jpg",
                media = emptyList()
            )
        )
    }
}
