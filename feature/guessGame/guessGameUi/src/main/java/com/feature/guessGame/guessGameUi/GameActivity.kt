package com.feature.guessGame.guessGameUi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.guessGame.guessGameUi.main.InstallSavedAppLanguage
import com.feature.guessGame.guessGameUi.navigation.Destinations
import com.feature.guessGame.guessGameUi.navigation.GuessGameNavGraph
import com.feature.guessGame.guessGameUi.navigation.fromJsonToDestination
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsUseCase: SettingsUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val gameDestination = intent.getStringExtra("gameDestination")
            ?.fromJsonToDestination()
            ?: Destinations.Screen

        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                GuessGameNavGraph(
                    destination = gameDestination,
                )
            }
        }
    }
}
