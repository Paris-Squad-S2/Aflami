package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.feature.guessGame.guessGameUi.R

data class GuessGameScreenUiState(
    val userPoints: Int = 0,
    val games: List<GameUiState> = listOf(GameUiState()),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedDifficulty: Int = Difficulty.EASY,
    val showDifficultyDialog: Boolean = false,
    val selectedGameId: String = "",
)

data class GameUiState(
    val isLocked: Boolean = false,
    val pointsToUnlock: Int = 0,
)

data class GameData(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val backgroundColors: List<Color> = emptyList(),
    val trailingImages: List<Painter> = emptyList(),
    val isLocked: Boolean = false,
    val pointsToUnlock: Int = 0,
)

data class DifficultySettings(
    val numberOfQuestions: Int,
    val timePerQuestionSec: Int,
    val pointsPerQuestion: Int,
) {
    companion object {
        private const val QUESTIONS_EASY = 5
        private const val TIME_EASY = 45
        private const val POINTS_EASY = 5

        private const val QUESTIONS_MEDIUM = 10
        private const val TIME_MEDIUM = 30
        private const val POINTS_MEDIUM = 10

        private const val QUESTIONS_HARD = 20
        private const val TIME_HARD = 10
        private const val POINTS_HARD = 20

        fun getDifficultySettings(difficultyId: Int) = when (difficultyId) {
            Difficulty.EASY -> DifficultySettings(QUESTIONS_EASY, TIME_EASY, POINTS_EASY)
            Difficulty.MEDIUM -> DifficultySettings(QUESTIONS_MEDIUM, TIME_MEDIUM, POINTS_MEDIUM)
            Difficulty.HARD -> DifficultySettings(QUESTIONS_HARD, TIME_HARD, POINTS_HARD)
            else -> DifficultySettings(QUESTIONS_EASY, TIME_EASY, POINTS_EASY)
        }
    }
}

object Difficulty {
    val EASY = R.string.Easy
    val MEDIUM = R.string.Medium
    val HARD = R.string.Hard
}
