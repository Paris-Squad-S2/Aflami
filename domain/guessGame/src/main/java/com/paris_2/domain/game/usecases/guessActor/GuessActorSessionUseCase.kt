package com.paris_2.domain.game.usecases.guessActor

import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.repositories.ActorPopularityRepository
import java.util.UUID

class GuessActorSessionUseCase(
    private val actorPopularityRepository: ActorPopularityRepository
) {

    private suspend fun generateQuestion(): Question {
        val actors = actorPopularityRepository.getRandomActors(4)
        val correctActor = actors.random()

        val questionId = UUID.randomUUID().toString()
        val options = actors.map {
            Answer(
                questionId = questionId,
                text = it.name,
                isCorrect = it.name == correctActor.name
            )
        }.shuffled()

        return Question(
            id = questionId,
            type = Question.QuestionType.IMAGE,
            content = correctActor.imageUri,
            correctAnswer = correctActor.name,
            options = options,
            usedHint = false
        )
    }

    suspend fun startNewSession(level: GameSession.GameLevel): GameSession {
        val numberOfQuestions = when (level) {
            GameSession.GameLevel.EASY -> 5
            GameSession.GameLevel.MEDIUM -> 10
            GameSession.GameLevel.HARD -> 20
        }

        val questions = (1..numberOfQuestions).map { generateQuestion() }

        return GameSession(
            id = UUID.randomUUID().toString(),
            level = level,
            questions = questions
        )
    }
}