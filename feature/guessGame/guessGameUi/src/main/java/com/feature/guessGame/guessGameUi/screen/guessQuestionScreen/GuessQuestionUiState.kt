package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

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
    val genreText: Int? = null,
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
    if (questionType == QuestionType.GENRE) {
        val genre = Genre.fromDisplayName(text)
        UiAnswer(
            text = genre?.displayName ?: text,
            genreText = genre?.toGenreUi()?.localizedName,
            isCorrect = isCorrect
        )
    } else {
        UiAnswer(
            text = text,
            isCorrect = isCorrect
        )
    }


data class GenreUi(val displayName: String, val localizedName: Int)

fun Genre.toGenreUi(): GenreUi = GenreUi(
    displayName = this.displayName,
    localizedName = when (this) {
        Genre.ACTION -> R.string.genre_action
        Genre.ADVENTURE -> R.string.genre_adventure
        Genre.ANIMATION -> R.string.genre_animation
        Genre.COMEDY -> R.string.genre_comedy
        Genre.CRIME -> R.string.genre_crime
        Genre.DOCUMENTARY -> R.string.genre_documentary
        Genre.DRAMA -> R.string.genre_drama
        Genre.FAMILY -> R.string.genre_family
        Genre.FANTASY -> R.string.genre_fantasy
        Genre.HISTORY -> R.string.genre_history
        Genre.HORROR -> R.string.genre_horror
        Genre.MUSIC -> R.string.genre_music
        Genre.MYSTERY -> R.string.genre_mystery
        Genre.ROMANCE -> R.string.genre_romance
        Genre.SCIENCE_FICTION -> R.string.genre_science_fiction
        Genre.TV_MOVIE -> R.string.genre_tv_movie
        Genre.THRILLER -> R.string.genre_thriller
        Genre.WAR -> R.string.genre_war
        Genre.WESTERN -> R.string.genre_western
        else -> R.string.genre_action
    }
)
