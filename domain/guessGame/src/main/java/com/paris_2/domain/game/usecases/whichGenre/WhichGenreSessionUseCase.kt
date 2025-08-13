package com.paris_2.domain.game.usecases.whichGenre

import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.repositories.ActorPopularityRepository
import com.paris_2.domain.game.utils.Genre
import java.util.UUID

class WhichGenreSessionUseCase(
    private val actorPopularityRepository: ActorPopularityRepository
) {

    private suspend fun generateQuestion(): Question {
        val actors = actorPopularityRepository.getPopularActor()
        val allMovies = actors.flatMap { it.media }.filter { it.genres.isNotEmpty() }

        val correctMovie = allMovies.random()
        val correctGenreId = correctMovie.genres.first()
        val correctGenreName = Genre.fromId(correctGenreId)?.displayName
            ?: throw IllegalStateException("Genre ID $correctGenreId not found")

        val questionId = UUID.randomUUID().toString()

        val wrongGenreNames = allMovies
            .flatMap { it.genres }
            .distinct()
            .filter { it != correctGenreId }
            .shuffled()
            .take(3)
            .mapNotNull { Genre.fromId(it)?.displayName }

        val allOptions = (wrongGenreNames + correctGenreName)
            .shuffled()
            .map { genreName ->
                Answer(
                    questionId = questionId,
                    selectedAnswer = genreName,
                    isCorrect = genreName == correctGenreName
                )
            }

        return Question(
            id = questionId,
            type = Question.QuestionType.TEXT,
            content = correctMovie.name,
            correctAnswer = correctGenreName,
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