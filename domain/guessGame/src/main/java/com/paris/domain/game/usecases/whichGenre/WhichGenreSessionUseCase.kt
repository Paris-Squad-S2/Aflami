package com.paris.domain.game.usecases.whichGenre

import com.paris.domain.game.entity.Answer
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import com.paris.domain.game.repositories.ActorPopularityRepository
import java.util.UUID

class WhichGenreSessionUseCase(
    private val actorPopularityRepository: ActorPopularityRepository,
) {

    private suspend fun generateQuestion(): Question {
        val actors = actorPopularityRepository.getPopularActor()
        val allMovies = actors.flatMap { it.media }.filter { it.genres.isNotEmpty() }

        val correctMovie = allMovies.random()
        val correctGenreName = correctMovie.genres.random()

        val questionId = UUID.randomUUID().toString()

        val wrongGenreNames = allMovies
            .flatMap { it.genres }
            .distinct()
            .filter { it != correctGenreName }
            .shuffled()
            .take(3)

        val allOptions = (wrongGenreNames + correctGenreName)
            .shuffled()
            .map { genreName ->
                Answer(
                    questionId = questionId,
                    text = genreName.toString(),
                    isCorrect = genreName == correctGenreName,
                    genre = genreName
                )
            }

        return Question(
            id = questionId,
            type = Question.QuestionType.TEXT,
            content = correctMovie.name,
            correctAnswer = correctGenreName.toString(),
            options = allOptions,
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