package com.feature.onboarding.onboardingUi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.onboarding.onboardingUi.ui.OnboardingScreen
import com.feature.onboarding.onboardingUi.ui.main.InstallSavedAppLanguage
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(isDarkTheme = settingsUseCase.isDarkTheme(), isLightStatusBars = false) {
                OnboardingScreen()
            }
        }
    }
}
