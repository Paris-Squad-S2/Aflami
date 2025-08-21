package com.paris.domain.game.usecases

import com.paris.domain.game.entity.Answer
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.domain.media.entity.Category
import java.util.UUID

class GenerateGuessMovieSessionUseCase(
    private val actorPopularityRepository: ActorPopularityRepository,
) {
    private suspend fun generateQuestion(): Question {

        val actors = actorPopularityRepository.getPopularActor()
        val allMovies = actors.flatMap { it.media }
        val correctMovie = allMovies.random()

        val wrongOptions = allMovies
            .filter { it.id != correctMovie.id }
            .map { it.name }
            .shuffled()
            .take(3)

        val questionId = UUID.randomUUID().toString()
        val options = (wrongOptions + correctMovie.name)
            .map { movieName ->
                Answer(
                    questionId = questionId,
                    text = movieName,
                    isCorrect = movieName == correctMovie.name,
                    genre = correctMovie.genres.firstOrNull() ?: Category.Unknown
                )
            }
            .shuffled()

        return Question(
            id = questionId,
            type = Question.QuestionType.IMAGE,
            content = correctMovie.posterImg,
            correctAnswer = correctMovie.name,
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