package com.feature.onboarding.onboardingUi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.onboarding.onboardingUi.navigation.OnBoardingNavGraph
import com.feature.onboarding.onboardingUi.navigation.OnBoardingNavigatorEntryPoint
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val navigator = EntryPointAccessors.fromApplication(
            applicationContext,
            OnBoardingNavigatorEntryPoint::class.java
        ).onBoardingNavigator()

        setContent {
            AflamiTheme {
                OnBoardingNavGraph(
                    navigator = navigator
                )
            }
        }
    }
}
