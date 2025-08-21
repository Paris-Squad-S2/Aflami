package com.paris.domain.game.usecases

import com.paris.domain.game.entity.Answer
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.domain.media.entity.Category
import java.util.UUID

class GenerateGuessActorSessionUseCase(
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
                isCorrect = it.name == correctActor.name,
                genre = it.media.firstOrNull()?.genres?.firstOrNull() ?: Category.Unknown
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