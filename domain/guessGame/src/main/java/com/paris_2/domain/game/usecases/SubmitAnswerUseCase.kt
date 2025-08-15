package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.GameSession

class SubmitAnswerUseCase {
    operator fun invoke(session: GameSession, selectedAnswer: String) {
        val question = session.getCurrentQuestion() ?: return
        if (!question.usedHint && question.options.any { it.isCorrect }) {
            if (selectedAnswer == question.correctAnswer) {
                session.score += if (question.usedHint) 5 else 10
            }
        }
    }
}