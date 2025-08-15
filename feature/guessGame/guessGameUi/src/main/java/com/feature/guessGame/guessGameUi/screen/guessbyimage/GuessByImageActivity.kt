package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.feature.guessGame.guessGameUi.common.main.InstallSavedAppLanguage
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuessByImageActivity() : AppCompatActivity() {

    private lateinit var viewModel: GuessByImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extract data from Intent
        val questionType = intent.getStringExtra("question_type") ?: QuestionType.POSTER.name
        val totalQuestions = intent.getIntExtra("total_questions", 10)
        val timePerQuestion = intent.getIntExtra("time_per_question", 30)
        val pointsPerQuestion = intent.getIntExtra("points_per_question", 10)
        val imageType = intent.getStringExtra("image_type")?.let { QuestionType.valueOf(it) }
            ?: QuestionType.ACTOR
        val gameLevel = intent.getStringExtra("game_level")?.let { UiGameLevel.valueOf(it) }
            ?: UiGameLevel.EASY

        // Initialize ViewModel and pass the data
        viewModel = ViewModelProvider(this)[GuessByImageViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme {
                GuessByImageScreen()
            }
        }
    }
}