package com.feature.onboarding.onboardingUi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.onboarding.onboardingUi.ui.main.InstallSavedAppLanguage
import com.paris.aflami.designsystem.theme.AflamiTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme {

            }
        }
    }

}
