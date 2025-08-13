package com.paris_2.domain.game.repositories

import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question

interface GameRepository {
    suspend fun getQuestion(type: Question.QuestionType): Question
    suspend fun submitAnswer(answer: String , time : Long): Boolean
    suspend fun getGameSession(id: String): GameSession?
    suspend fun newGameSession(level: GameSession.GameLevel): GameSession
    //TODO think about creating a memory dataSource for this game
}