package com.feature.onboarding.onboardingUi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.onboarding.onboardingUi.ui.OnboardingScreen
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AflamiTheme {
                OnboardingScreen()
            }
        }
    }
}
