package com.paris_2.domain.game.usecases.whichGenre

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.game.entity.Actor
import com.paris_2.domain.game.entity.ActorMedia
import com.paris_2.domain.game.entity.GameSession
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.repositories.ActorPopularityRepository
import com.paris_2.domain.game.utils.Genre
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhichGenreSessionUseCaseTest {

    private lateinit var repository: ActorPopularityRepository
    private lateinit var useCase: WhichGenreSessionUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = WhichGenreSessionUseCase(repository)
        coEvery { repository.getPopularActor() } returns sampleActors
    }

    @Test
    fun `should create 5 questions for EASY with valid genre options`() = runTest {
        val session = useCase.startNewSession(GameSession.GameLevel.EASY)
        assertThat(session.questions).hasSize(5)
        session.questions.forEach { q -> assertValidQuestion(q) }
        coVerify(exactly = 5) { repository.getPopularActor() }
    }

    @Test
    fun `should create 10 questions for MEDIUM`() = runTest {
        val session = useCase.startNewSession(GameSession.GameLevel.MEDIUM)
        assertThat(session.questions).hasSize(10)
        coVerify(exactly = 10) { repository.getPopularActor() }
    }

    @Test
    fun `should create 20 questions for HARD`() = runTest {
        val session = useCase.startNewSession(GameSession.GameLevel.HARD)
        assertThat(session.questions).hasSize(20)
        coVerify(exactly = 20) { repository.getPopularActor() }
    }

    private fun assertValidQuestion(q: Question) {
        val allMovies = sampleActors.flatMap { it.media }
        val allDisplayNames = Genre.values().map { it.displayName }.toSet()
        assertThat(q.type).isEqualTo(Question.QuestionType.TEXT)
        assertThat(q.usedHint).isFalse()
        assertThat(q.options).hasSize(4)
        val movie = allMovies.find { it.name == q.content }
        assertThat(movie).isNotNull()
        val movieGenreNames = movie!!.genres.mapNotNull { Genre.fromId(it)?.displayName }.toSet()
        assertThat(movieGenreNames).isNotEmpty()
        assertThat(movieGenreNames).contains(q.correctAnswer)
        assertThat(q.options.map { it.text }).contains(q.correctAnswer)
        assertThat(q.options.count { it.isCorrect }).isEqualTo(1)
        assertThat(q.options.all { it.text in allDisplayNames }).isTrue()
    }

    private companion object {
        private val actor1Movies = listOf(
            ActorMedia(
                id = 1,
                name = "Movie A",
                posterImg = "/pA.jpg",
                yearOfRelease = LocalDate(2020, 1, 1),
                genres = listOf(Genre.ACTION.id, Genre.DRAMA.id)
            ),
            ActorMedia(
                id = 2,
                name = "Movie B",
                posterImg = "/pB.jpg",
                yearOfRelease = LocalDate(2021, 2, 2),
                genres = listOf(Genre.COMEDY.id)
            )
        )
        private val actor2Movies = listOf(
            ActorMedia(
                id = 3,
                name = "Movie C",
                posterImg = "/pC.jpg",
                yearOfRelease = LocalDate(2019, 3, 3),
                genres = listOf(Genre.CRIME.id)
            )
        )
        private val actor3Movies = listOf(
            ActorMedia(
                id = 4,
                name = "Movie D",
                posterImg = "/pD.jpg",
                yearOfRelease = LocalDate(2018, 4, 4),
                genres = listOf(Genre.ADVENTURE.id)
            )
        )
        private val actor4Movies = listOf(
            ActorMedia(
                id = 5,
                name = "Movie E",
                posterImg = "/pE.jpg",
                yearOfRelease = LocalDate(2017, 5, 5),
                genres = listOf(Genre.FANTASY.id)
            )
        )

        private val sampleActors = listOf(
            Actor(id = 10, name = "Actor One", imageUri = "/img1.jpg", media = actor1Movies),
            Actor(id = 20, name = "Actor Two", imageUri = "/img2.jpg", media = actor2Movies),
            Actor(id = 30, name = "Actor Three", imageUri = "/img3.jpg", media = actor3Movies),
            Actor(id = 40, name = "Actor Four", imageUri = "/img4.jpg", media = actor4Movies)
        )
    }
}
