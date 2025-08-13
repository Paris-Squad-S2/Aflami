package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import kotlin.random.Random

class RemoveAnswerHintUseCase(
    private val requiredPointsForHint: Int = 10
) {

    operator fun invoke(
        gameSession: GameSession,
        usedHint: Boolean,
        currentPoints: Int
    ): UseHintResult {
        if (currentPoints <= requiredPointsForHint) {
            return UseHintResult.NotEnoughPoints
        }

        if (usedHint) {
            return UseHintResult.AlreadyUsed
        }

        val currentQuestion = gameSession.getCurrentQuestion()
            ?: throw IllegalStateException("No current question found")

        val correctAnswer = currentQuestion.correctAnswer
        val wrongAnswers = currentQuestion.options.filter { it.selectedAnswer != correctAnswer }

        if (wrongAnswers.isEmpty()) {
            return UseHintResult.Success(currentQuestion)
        }

        val optionToRemove = wrongAnswers[Random.Default.nextInt(wrongAnswers.size)]
        val updatedOptions =
            currentQuestion.options.filter { it.selectedAnswer != optionToRemove.selectedAnswer }

        val updatedQuestion = currentQuestion.copy(
            options = updatedOptions
        )

        return UseHintResult.Success(updatedQuestion)
    }

    sealed class UseHintResult {
        data class Success(val updatedQuestion: Question) : UseHintResult()
        object AlreadyUsed : UseHintResult()
        object NotEnoughPoints : UseHintResult()
    }
}