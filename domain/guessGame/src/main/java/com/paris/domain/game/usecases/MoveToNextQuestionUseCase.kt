package com.paris.domain.game.usecases

import com.paris.domain.game.entity.GameSession

class MoveToNextQuestionUseCase {
    operator fun invoke(gameSession: GameSession): GameSession {
        gameSession.moveToNextQuestion()
        return gameSession
    }
}