package com.paris_2.aflami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.paris_2.domain.user.usecase.HasAnySessionUseCase
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.paris_2.aflami.bottomNavBar.AppNavigationAPI
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.user.usecase.IsOnboardingCompletedUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationFeatureAPI: AuthenticationFeatureAPI
    @Inject
    lateinit var hasAnySessionUseCase: HasAnySessionUseCase
    @Inject
    lateinit var appNavigationAPI: AppNavigationAPI
    @Inject
    lateinit var isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase
    @Inject
    lateinit var onBoardingApI: OnBoardingFeatureAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                when {
                    hasAnySessionUseCase() -> appNavigationAPI()
                    isOnboardingCompletedUseCase() -> authenticationFeatureAPI()
                    else -> onBoardingApI()
                }
            }
        }
    }
}