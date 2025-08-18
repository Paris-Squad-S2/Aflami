package com.paris_2.domain.game.usecases.whenIsReleased

import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.repositories.ActorPopularityRepository
import com.paris_2.domain.game.utils.Genre
import java.util.UUID

class WhenIsReleasedSessionUseCase(
    private val actorPopularityRepository: ActorPopularityRepository
) {

    private suspend fun generateQuestion(): Question {
        val actors = actorPopularityRepository.getPopularActor()
        val allMovies = actors.flatMap { it.media }

        val correctMovie = allMovies.random()

        val questionId = UUID.randomUUID().toString()

        val wrongYears = allMovies
            .filter { it.id != correctMovie.id }
            .map { it.yearOfRelease.year }
            .distinct()
            .shuffled()
            .take(3)

        val allOptions = (wrongYears + correctMovie.yearOfRelease.year)
            .shuffled()
            .map { year ->
                Answer(
                    questionId = questionId,
                    text = year.toString(),
                    isCorrect = year == correctMovie.yearOfRelease.year,
                    genre = correctMovie.genres.firstOrNull() ?: Genre.UNKNOWN
                )
            }

        return Question(
            id = questionId,
            type = Question.QuestionType.TEXT,
            content = correctMovie.name,
            correctAnswer = correctMovie.yearOfRelease.year.toString(),
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