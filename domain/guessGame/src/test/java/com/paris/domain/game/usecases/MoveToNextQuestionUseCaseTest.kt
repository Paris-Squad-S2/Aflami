package com.paris.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoveToNextQuestionUseCaseTest {

    private lateinit var useCase: MoveToNextQuestionUseCase

    @BeforeEach
    fun setUp() {
        useCase = MoveToNextQuestionUseCase()
    }

    @Test
    fun `should move to next question when not at last`() {
        // Given
        val questions = listOf(q("1"), q("2"), q("3"))
        val session = GameSession(
            id = "s1",
            level = GameSession.GameLevel.EASY,
            questions = questions,
            currentQuestionIndex = 0
        )
        // When
        val result = useCase(session)
        // Then
        assertThat(result === session).isTrue()
        assertThat(session.currentQuestionIndex).isEqualTo(1)
        assertThat(session.isCompleted).isFalse()
        assertThat(session.getCurrentQuestion()?.id).isEqualTo("2")
    }

    @Test
    fun `should mark session completed when at last question`() {
        // Given
        val questions = listOf(q("1"), q("2"), q("3"))
        val session = GameSession(
            id = "s1",
            level = GameSession.GameLevel.MEDIUM,
            questions = questions,
            currentQuestionIndex = questions.lastIndex
        )
        // When
        useCase(session)
        // Then
        assertThat(session.isCompleted).isTrue()
        assertThat(session.currentQuestionIndex).isEqualTo(questions.lastIndex)
    }

    @Test
    fun `should mark session completed when there are no questions`() {
        // Given
        val session = GameSession(
            id = "sEmpty",
            level = GameSession.GameLevel.HARD,
            questions = emptyList()
        )
        // When
        useCase(session)
        // Then
        assertThat(session.isCompleted).isTrue()
        assertThat(session.currentQuestionIndex).isEqualTo(0)
        assertThat(session.getCurrentQuestion()).isNull()
    }

    private fun q(id: String): Question = Question(
        id = id,
        type = Question.QuestionType.TEXT,
        content = "content $id",
        correctAnswer = "A",
        options = emptyList(),
        usedHint = false
    )
}
