package com.feature.onboarding.onboardingUi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
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

                val context = LocalContext.current
                val view = LocalView.current
                val activity = context as? ComponentActivity

                LaunchedEffect(Unit) {
                    activity?.window?.also { window ->
                        WindowInsetsControllerCompat(window, view).apply {
                            isAppearanceLightStatusBars = false
                            isAppearanceLightNavigationBars = false
                        }
                    }
                }

                OnboardingScreen()
            }
        }
    }
}
