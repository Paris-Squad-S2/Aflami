package com.paris_2.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.entity.UserPoints
import com.paris_2.domain.game.repositories.GamePointsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UseHintUseCaseTest {

    private lateinit var repository: GamePointsRepository
    private lateinit var useCase: UseHintUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = UseHintUseCase(repository)
    }

    @Test
    fun `should set hint used and deduct 10 points when user has enough points`() = runTest {
        // Given
        val session = sessionWithQuestion(question("q1", correct = "A", usedHint = false))
        coEvery { repository.getUserGamePoints(USER_ID) } returns UserPoints(USER_ID, 25)
        // When
        useCase(session, USER_ID)
        // Then
        assertThat(session.questions.first().usedHint).isTrue()
        coVerify(exactly = 1) { repository.saveUserGamePoints(UserPoints(USER_ID, 15)) }
    }

    @Test
    fun `should set hint used and reduce points to zero when exactly cost`() = runTest {
        // Given
        val session = sessionWithQuestion(question("q1", correct = "A", usedHint = false))
        coEvery { repository.getUserGamePoints(USER_ID) } returns UserPoints(USER_ID, 10)
        // When
        useCase(session, USER_ID)
        // Then
        assertThat(session.questions.first().usedHint).isTrue()
        coVerify(exactly = 1) { repository.saveUserGamePoints(UserPoints(USER_ID, 0)) }
    }

    @Test
    fun `should not set hint or save when user has insufficient points`() = runTest {
        // Given
        val session = sessionWithQuestion(question("q1", correct = "A", usedHint = false))
        coEvery { repository.getUserGamePoints(USER_ID) } returns UserPoints(USER_ID, 5)
        // When
        useCase(session, USER_ID)
        // Then
        assertThat(session.questions.first().usedHint).isFalse()
        coVerify(exactly = 0) { repository.saveUserGamePoints(any()) }
    }

    @Test
    fun `should deduct points even if there is no current question`() = runTest {
        // Given
        val session = GameSession(
            id = "s0",
            level = GameSession.GameLevel.EASY,
            questions = emptyList()
        )
        coEvery { repository.getUserGamePoints(USER_ID) } returns UserPoints(USER_ID, 20)
        // When
        useCase(session, USER_ID)
        // Then
        coVerify(exactly = 1) { repository.saveUserGamePoints(UserPoints(USER_ID, 10)) }
    }

    // Helpers
    private fun sessionWithQuestion(question: Question) = GameSession(
        id = "s1",
        level = GameSession.GameLevel.EASY,
        questions = listOf(question)
    )

    private fun question(
        id: String,
        correct: String,
        usedHint: Boolean
    ) = Question(
        id = id,
        type = Question.QuestionType.TEXT,
        content = "content",
        correctAnswer = correct,
        options = listOf(
            Answer(id, correct, isCorrect = true),
            Answer(id, "other", isCorrect = false)
        ),
        usedHint = usedHint
    )

    private companion object {
        const val USER_ID = 101
    }
}
