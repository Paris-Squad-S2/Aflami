package com.paris_2.domain.game.usecases.guessActor

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.Actor
import com.paris_2.domain.game.entity.ActorMedia
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.repositories.ActorPopularityRepository
import com.paris_2.domain.media.entity.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GuessActorSessionUseCaseTest {

    private lateinit var repository: ActorPopularityRepository
    private lateinit var useCase: GuessActorSessionUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GuessActorSessionUseCase(repository)
        coEvery { repository.getRandomActors(4) } returns sampleActors
    }

    @Test
    fun `should create 5 questions for EASY level with valid options`() = runTest {
        // When
        val session = useCase.startNewSession(GameSession.GameLevel.EASY)
        // Then
        assertThat(session.id).isNotEmpty()
        assertThat(session.level).isEqualTo(GameSession.GameLevel.EASY)
        assertThat(session.questions).hasSize(5)
        session.questions.forEach { q ->
            assertThat(q.type).isEqualTo(com.paris_2.domain.game.entity.Question.QuestionType.IMAGE)
            assertThat(q.usedHint).isFalse()
            assertThat(q.options).hasSize(4)
            assertThat(sampleActors.map { it.imageUri }).contains(q.content)
            assertThat(sampleActors.map { it.name }).contains(q.correctAnswer)
            assertThat(q.options.count { it.isCorrect }).isEqualTo(1)
            assertThat(q.options.map { it.text }.toSet())
                .isEqualTo(sampleActors.map { it.name }.toSet())
        }
        coVerify(exactly = 5) { repository.getRandomActors(4) }
    }

    @Test
    fun `should create 10 questions for MEDIUM level`() = runTest {
        val session = useCase.startNewSession(GameSession.GameLevel.MEDIUM)
        assertThat(session.questions).hasSize(10)
        coVerify(exactly = 10) { repository.getRandomActors(4) }
    }

    @Test
    fun `should create 20 questions for HARD level`() = runTest {
        val session = useCase.startNewSession(GameSession.GameLevel.HARD)
        assertThat(session.questions).hasSize(20)
        coVerify(exactly = 20) { repository.getRandomActors(4) }
    }

    private companion object {
        private val sampleActors = listOf(
            Actor(
                id = 1,
                name = "Actor One",
                imageUri = "/img1.jpg",
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
                imageUri = "/img2.jpg",
                media = emptyList()
            ),
            Actor(
                id = 3,
                name = "Actor Three",
                imageUri = "/img3.jpg",
                media = emptyList()
            ),
            Actor(
                id = 4,
                name = "Actor Four",
                imageUri = "/img4.jpg",
                media = emptyList()
            )
        )
    }
}
