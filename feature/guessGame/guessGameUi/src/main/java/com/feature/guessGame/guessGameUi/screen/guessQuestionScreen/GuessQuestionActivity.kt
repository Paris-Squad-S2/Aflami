package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.feature.guessGame.guessGameUi.common.main.InstallSavedAppLanguage
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.feature.guessGame.guessGameUi.screen.guessbyimage.GuessByImageScreen
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuessQuestionActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme {
                GuessQuestionScreen(this)
            }
        }
    }
}