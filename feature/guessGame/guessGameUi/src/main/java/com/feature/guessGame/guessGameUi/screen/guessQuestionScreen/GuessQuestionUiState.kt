package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import GenreGameUi
import androidx.annotation.StringRes
import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris.domain.game.entity.Answer
import com.paris.domain.game.entity.Question
import com.paris.domain.media.entity.Category

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

fun Category.toUi(): GenreGameUi = when (this) {
    Category.Action -> GenreGameUi.Action
    Category.Adventure -> GenreGameUi.Adventure
    Category.Animation -> GenreGameUi.Animation
    Category.Comedy -> GenreGameUi.Comedy
    Category.Crime -> GenreGameUi.Crime
    Category.Documentary -> GenreGameUi.Documentary
    Category.Drama -> GenreGameUi.Drama
    Category.Family -> GenreGameUi.Family
    Category.Fantasy -> GenreGameUi.Fantasy
    Category.History -> GenreGameUi.History
    Category.Horror -> GenreGameUi.Horror
    Category.Music -> GenreGameUi.Music
    Category.Mystery -> GenreGameUi.Mystery
    Category.Romance -> GenreGameUi.Romance
    Category.ScienceFiction -> GenreGameUi.ScienceFiction
    Category.TvMovie -> GenreGameUi.TvMovie
    Category.Thriller -> GenreGameUi.Thriller
    Category.War -> GenreGameUi.War
    Category.Western -> GenreGameUi.Western
    Category.ActionAdventure -> GenreGameUi.ActionAdventure
    Category.Kids -> GenreGameUi.Kids
    Category.News -> GenreGameUi.News
    Category.Reality -> GenreGameUi.Reality
    Category.ScifiFantasy -> GenreGameUi.ScifiFantasy
    Category.Soap -> GenreGameUi.Soap
    Category.Talk -> GenreGameUi.Talk
    Category.WarPolitics -> GenreGameUi.WarPolitics
    Category.Unknown -> GenreGameUi.Unknown
}

fun Category.toDisplayName(): Int = this.toUi().displayNameResId
