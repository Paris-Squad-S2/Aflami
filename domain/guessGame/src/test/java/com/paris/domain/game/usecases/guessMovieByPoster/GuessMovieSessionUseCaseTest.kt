package com.paris.domain.game.usecases.guessMovieByPoster

import com.google.common.truth.Truth.assertThat
import com.paris.domain.game.entity.Actor
import com.paris.domain.game.entity.ActorMedia
import com.paris.domain.game.entity.GameSession
import com.paris.domain.game.entity.Question
import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.domain.game.usecases.GenerateGuessMovieSessionUseCase
import com.paris.domain.media.entity.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GuessMovieSessionUseCaseTest {

    private lateinit var repository: ActorPopularityRepository
    private lateinit var useCase: GenerateGuessMovieSessionUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GenerateGuessMovieSessionUseCase(repository)
        coEvery { repository.getPopularActor() } returns sampleActors
    }

    @Test
    fun `should create requested number of poster questions with valid options`() = runTest {
        // Given
        val count = 7
        val allMovies = sampleActors.flatMap { it.media }
        // When
        val session: GameSession = useCase.startNewSession(GameSession.GameLevel.MEDIUM)
        val questions: List<Question> = session.questions
        // Then
        assertThat(questions).hasSize(10) // MEDIUM level is 10 questions
        questions.forEach { q ->
            assertThat(q.options).hasSize(4)
            assertThat(q.options.map { it.text }).contains(q.correctAnswer)
            assertThat(allMovies.map { it.name }).contains(q.correctAnswer)
            val movieWithPoster = allMovies.find { it.posterImg == q.content }
            assertThat(movieWithPoster).isNotNull()
            assertThat(movieWithPoster!!.name).isEqualTo(q.correctAnswer)
        }
        coVerify(exactly = 10) { repository.getPopularActor() }
    }

    @Test
    fun `should return empty list when count is zero`() = runTest {
        // When
        val session = useCase.startNewSession(GameSession.GameLevel.EASY)
        // Then
        assertThat(session.questions).hasSize(5) // EASY level is 5 questions
        coVerify(exactly = 5) { repository.getPopularActor() }
    }

    private companion object {
        private val actor1Movies = listOf(
            ActorMedia(
                id = 1,
                name = "Movie A",
                posterImg = "/pA.jpg",
                yearOfRelease = LocalDate(2020, 1, 1),
                genres = listOf(Category.ScifiFantasy)
            ),
            ActorMedia(
                id = 2,
                name = "Movie B",
                posterImg = "/pB.jpg",
                yearOfRelease = LocalDate(2021, 2, 2),
                genres = listOf( Category.ActionAdventure)
            )
        )
        private val actor2Movies = listOf(
            ActorMedia(
                id = 3,
                name = "Movie C",
                posterImg = "/pC.jpg",
                yearOfRelease = LocalDate(2019, 3, 3),
                genres = listOf(Category.TvMovie)
            )
        )
        private val actor3Movies = listOf(
            ActorMedia(
                id = 4,
                name = "Movie D",
                posterImg = "/pD.jpg",
                yearOfRelease = LocalDate(2018, 4, 4),
                genres = listOf(Category.War)
            )
        )

        private val sampleActors = listOf(
            Actor(id = 10, name = "Actor One", imageUri = "/img1.jpg", media = actor1Movies),
            Actor(id = 20, name = "Actor Two", imageUri = "/img2.jpg", media = actor2Movies),
            Actor(id = 30, name = "Actor Three", imageUri = "/img3.jpg", media = actor3Movies)
        )
    }
}
