package com.feature.home.homeUi.screen.continueWatching

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.feature.home.homeUi.screen.main.InstallSavedAppLanguage
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContinueWatchingActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsUseCase: SettingsUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                ContinueWatchingScreen()
            }
        }
    }
}