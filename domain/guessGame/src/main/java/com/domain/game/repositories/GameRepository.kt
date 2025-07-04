package com.domain.game.repositories

import com.domain.game.models.GameSession
import com.domain.game.models.Question

interface GameRepository {
    suspend fun getQuestion(type: Question.QuestionType): Question
    suspend fun submitAnswer(answer: String , time : Long): Boolean
    suspend fun getGameSession(id: String): GameSession?
    suspend fun newGameSession(level: GameSession.GameLevel): GameSession
    //TODO think about creating a memory dataSource for this game
}