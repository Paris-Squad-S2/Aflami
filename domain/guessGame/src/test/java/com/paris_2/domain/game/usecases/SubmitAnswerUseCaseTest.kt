package com.paris_2.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SubmitAnswerUseCaseTest {

    private lateinit var useCase: SubmitAnswerUseCase

    @BeforeEach
    fun setUp() {
        useCase = SubmitAnswerUseCase()
    }

    @Test
    fun `should add 10 points for correct answer when no hint used`() {
        // Given
        val first = question(
            id = "q1",
            correct = "A",
            usedHint = false,
            options = listOf(
                answer("q1", "A", isCorrect = true),
                answer("q1", "B", isCorrect = false)
            )
        )
        val session = gameSession(questions = listOf(first, question("q2", "X")))
        // When
        useCase(session, selectedAnswer = "A")
        // Then
        assertThat(session.score).isEqualTo(10)
        assertThat(session.currentQuestionIndex).isEqualTo(1)
        assertThat(session.isCompleted).isFalse()
    }

    @Test
    fun `should not add points when hint used even if answer is correct`() {
        // Given
        val first = question(
            id = "q1",
            correct = "A",
            usedHint = true,
            options = listOf(
                answer("q1", "A", isCorrect = true),
                answer("q1", "B", isCorrect = false)
            )
        )
        val session = gameSession(questions = listOf(first, question("q2", "X")))
        // When
        useCase(session, selectedAnswer = "A")
        // Then
        assertThat(session.score).isEqualTo(0)
        assertThat(session.currentQuestionIndex).isEqualTo(1)
    }

    @Test
    fun `should not add points for incorrect answer`() {
        // Given
        val first = question(
            id = "q1",
            correct = "A",
            usedHint = false,
            options = listOf(
                answer("q1", "A", isCorrect = true),
                answer("q1", "B", isCorrect = false)
            )
        )
        val session = gameSession(questions = listOf(first, question("q2", "X")))
        // When
        useCase(session, selectedAnswer = "B")
        // Then
        assertThat(session.score).isEqualTo(0)
        assertThat(session.currentQuestionIndex).isEqualTo(1)
    }

    @Test
    fun `should not add points when no correct options exist`() {
        // Given
        val first = question(
            id = "q1",
            correct = "A",
            usedHint = false,
            options = listOf(
                answer("q1", "A", isCorrect = false),
                answer("q1", "B", isCorrect = false)
            )
        )
        val session = gameSession(questions = listOf(first, question("q2", "X")))
        // When
        useCase(session, selectedAnswer = "A")
        // Then
        assertThat(session.score).isEqualTo(0)
        assertThat(session.currentQuestionIndex).isEqualTo(1)
    }

    @Test
    fun `should do nothing when there is no current question`() {
        // Given
        val session = gameSession(questions = emptyList())
        // When
        useCase(session, selectedAnswer = "A")
        // Then
        assertThat(session.score).isEqualTo(0)
        assertThat(session.currentQuestionIndex).isEqualTo(0)
        assertThat(session.isCompleted).isFalse()
    }

    private fun gameSession(questions: List<Question>) = GameSession(
        id = "s1",
        level = GameSession.GameLevel.EASY,
        questions = questions
    )

    private fun question(
        id: String,
        correct: String,
        usedHint: Boolean = false,
        options: List<Answer> = listOf(
            answer(id, correct, isCorrect = true),
            answer(id, "other", isCorrect = false)
        )
    ) = Question(
        id = id,
        type = Question.QuestionType.TEXT,
        content = "content",
        correctAnswer = correct,
        options = options,
        usedHint = usedHint
    )

    private fun answer(questionId: String, value: String, isCorrect: Boolean) = Answer(
        questionId = questionId,
        text = value,
        isCorrect = isCorrect
    )
}
