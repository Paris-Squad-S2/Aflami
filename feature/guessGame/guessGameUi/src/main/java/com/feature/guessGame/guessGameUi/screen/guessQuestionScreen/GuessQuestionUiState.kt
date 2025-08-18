package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import GenreGameUi
import androidx.annotation.StringRes
import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.Question
import com.paris_2.domain.game.utils.Genre

data class GuessQuestionUiState(
    val gameTitle: String = "",
    val totalQuestions: Int = 0,
    val currentStep: Int = 0,
    val questionUiState: List<UiQuestion> = emptyList(),
    val questionText: String = "",
    val answers: List<UiAnswer> = emptyList(),
    val correctAnswer: String? = null,
    val remainingAnswers: List<UiAnswer> = emptyList(),
    val selectedAnswer: String? = null,
    val hintUsed: Boolean = false,
    val showNotEnoughPointsDialog: Boolean = false,
    val timePerQuestion: Int = 0,
    val pointsPerQuestion: Int = 0,
    val time: Int = 0,
    val duration: Int = 0,
    val session: GameSessionUi? = GameSessionUi(),
    val error: String? = null,
    val isLoading: Boolean = false,
)

fun QuestionType.getTitleResId(): Int = when (this) {
    QuestionType.GENRE -> R.string.which_genre_title
    QuestionType.RELEASE_YEAR -> R.string.when_was_it_released_title
    QuestionType.ACTOR -> R.string.Guess_the_character
    QuestionType.POSTER -> R.string.guess_the_poster
}

data class GameSessionUi(
    val level: String = UiGameLevel.EASY.name,
    val currentQuestion: String = "",
    val score: Int = 0,
    val isCompleted: Boolean = false,
)


data class UiQuestion(
    val content: String,
    val options: List<UiAnswer>,
    val selectedAnswer: String? = null,
    val hintUsed: Boolean = false,
)

data class UiAnswer(
    val text: String,
    @StringRes val genreText: Int? = null,
    val isCorrect: Boolean,
)

fun Question.toUiQuestion(questionType: QuestionType): UiQuestion {
    return UiQuestion(
        content = content,
        options = options.map { it.toUiAnswer(questionType) },
        selectedAnswer = selectedAnswer,
        hintUsed = usedHint,

        )
}

fun Answer.toUiAnswer(questionType: QuestionType): UiAnswer =
    if (questionType == QuestionType.GENRE && genre != null) {
        UiAnswer(
            text = text.orEmpty(),
            genreText = genre.toDisplayName(),
            isCorrect = isCorrect
        )
    } else {
        UiAnswer(
            text = text.orEmpty(),
            isCorrect = isCorrect
        )
    }

fun Genre.toUi(): GenreGameUi = when (this) {
    Genre.ACTION -> GenreGameUi.Action
    Genre.ADVENTURE -> GenreGameUi.Adventure
    Genre.ANIMATION -> GenreGameUi.Animation
    Genre.COMEDY -> GenreGameUi.Comedy
    Genre.CRIME -> GenreGameUi.Crime
    Genre.DOCUMENTARY -> GenreGameUi.Documentary
    Genre.DRAMA -> GenreGameUi.Drama
    Genre.FAMILY -> GenreGameUi.Family
    Genre.FANTASY -> GenreGameUi.Fantasy
    Genre.HISTORY -> GenreGameUi.History
    Genre.HORROR -> GenreGameUi.Horror
    Genre.MUSIC -> GenreGameUi.Music
    Genre.MYSTERY -> GenreGameUi.Mystery
    Genre.ROMANCE -> GenreGameUi.Romance
    Genre.SCIENCE_FICTION -> GenreGameUi.ScienceFiction
    Genre.TV_MOVIE -> GenreGameUi.TvMovie
    Genre.THRILLER -> GenreGameUi.Thriller
    Genre.WAR -> GenreGameUi.War
    Genre.WESTERN -> GenreGameUi.Western
    Genre.ACTION_ADVENTURE -> GenreGameUi.ActionAdventure
    Genre.KIDS -> GenreGameUi.Kids
    Genre.NEWS -> GenreGameUi.News
    Genre.REALITY -> GenreGameUi.Reality
    Genre.SCI_FI_FANTASY -> GenreGameUi.ScifiFantasy
    Genre.SOAP -> GenreGameUi.Soap
    Genre.TALK -> GenreGameUi.Talk
    Genre.WAR_POLITICS -> GenreGameUi.WarPolitics
    Genre.UNKNOWN -> GenreGameUi.Unknown
}

fun Genre.toDisplayName(): Int = this.toUi().displayNameResId
