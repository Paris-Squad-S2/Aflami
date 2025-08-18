package com.paris_2.domain.game.usecases

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.media.entity.Category
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveAnswerHintUseCaseTest {

    private lateinit var useCase: RemoveAnswerHintUseCase

    @BeforeEach
    fun setUp() {
        useCase = RemoveAnswerHintUseCase()
    }

    @Test
    fun `should return NotEnoughPoints when points are less than or equal to required`() {
        // Given
        val session =
            sessionWithQuestion(questionWithAnswers(correct = "A", wrong = listOf("B", "C", "D")))
        val usedHint = false
        val currentPoints = 10
        // When
        val result = useCase(session, usedHint, currentPoints)
        // Then
        assertThat(result).isInstanceOf(RemoveAnswerHintUseCase.UseHintResult.NotEnoughPoints::class.java)
    }

    @Test
    fun `should return AlreadyUsed when hint already used`() {
        // Given
        val session =
            sessionWithQuestion(questionWithAnswers(correct = "A", wrong = listOf("B", "C")))
        val usedHint = true
        val currentPoints = 50
        // When
        val result = useCase(session, usedHint, currentPoints)
        // Then
        assertThat(result).isInstanceOf(RemoveAnswerHintUseCase.UseHintResult.AlreadyUsed::class.java)
    }

    @Test
    fun `should throw when there is no current question`() {
        // Given
        val emptySession = GameSession(
            id = "s0",
            level = GameSession.GameLevel.EASY,
            questions = emptyList()
        )
        // When / Then
        try {
            useCase(emptySession, usedHint = false, currentPoints = 20)
            throw AssertionError("Expected IllegalStateException for missing current question")
        } catch (e: IllegalStateException) {
            assertThat(e.message).contains("No current question")
        }
    }

    @Test
    fun `should return same question when there are no wrong answers`() {
        // Given
        val correct = "A"
        val q = Question(
            id = "q1",
            type = Question.QuestionType.TEXT,
            content = "content",
            correctAnswer = correct,
            options = listOf(
                answer("q1", correct, isCorrect = true, genre = Category.ActionAdventure),
                answer("q1", correct, isCorrect = true, genre = Category.ScienceFiction),
                answer("q1", correct, isCorrect = true, genre = Category.Thriller)
            ),
            usedHint = false
        )
        val session = sessionWithQuestion(q)
        // When
        val result = useCase(session, usedHint = false, currentPoints = 20)
        // Then
        assertThat(result).isInstanceOf(RemoveAnswerHintUseCase.UseHintResult.Success::class.java)
        val updated = (result as RemoveAnswerHintUseCase.UseHintResult.Success).updatedQuestion
        assertThat(updated).isEqualTo(q)
        assertThat(updated.options).hasSize(3)
        assertThat(session.questions.first().options).hasSize(3)
    }

    @Test
    fun `should remove one wrong answer when wrong answers exist and keep correct answer`() {
        // Given
        val correct = "A"
        val wrong = listOf("B", "C", "D")
        val q = questionWithAnswers(correct = correct, wrong = wrong)
        val session = sessionWithQuestion(q)
        val originalWrongCount = q.options.count { it.text != correct }
        val originalSize = q.options.size
        // When
        val result = useCase(session, usedHint = false, currentPoints = 20)
        // Then
        assertThat(result).isInstanceOf(RemoveAnswerHintUseCase.UseHintResult.Success::class.java)
        val updated = (result as RemoveAnswerHintUseCase.UseHintResult.Success).updatedQuestion
        assertThat(updated.options).hasSize(originalSize - 1)
        assertThat(updated.options.any { it.text == correct }).isTrue()
        val updatedWrongCount = updated.options.count { it.text != correct }
        assertThat(updatedWrongCount).isEqualTo(originalWrongCount - 1)
        assertThat(session.questions.first().options).hasSize(originalSize)
    }

    private fun sessionWithQuestion(question: Question): GameSession = GameSession(
        id = "s1",
        level = GameSession.GameLevel.EASY,
        questions = listOf(question)
    )

    private fun questionWithAnswers(correct: String, wrong: List<String>): Question = Question(
        id = "q1",
        type = Question.QuestionType.TEXT,
        content = "content",
        correctAnswer = correct,
        options = buildList {
            add(answer("q1", correct, isCorrect = true , genre = Category.WarPolitics))
            wrong.forEach { add(answer("q1", it, isCorrect = false, genre = Category.War)) }
        },
        usedHint = false
    )

    private fun answer(questionId: String, value: String, isCorrect: Boolean,genre: Category) = Answer(
        questionId = questionId,
        text = value,
        isCorrect = isCorrect,
        genre = genre
    )
}
