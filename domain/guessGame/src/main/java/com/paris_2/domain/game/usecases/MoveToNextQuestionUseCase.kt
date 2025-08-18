package com.paris_2.domain.game.usecases

import com.paris_2.domain.game.entity.GameSession

class MoveToNextQuestionUseCase {
    operator fun invoke(gameSession: GameSession): GameSession {
        gameSession.moveToNextQuestion()
        return gameSession
    }
}