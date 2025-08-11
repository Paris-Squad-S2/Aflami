package com.paris_2.aflami

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.main.InstallSavedAppLanguage
import com.paris_2.domain.user.usecase.HasAnySessionUseCase
import com.paris_2.domain.user.usecase.IsOnboardingCompletedUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CoroutineCreationDuringComposition")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase

    @Inject
    lateinit var onBoardingApI: OnBoardingFeatureAPI

    @Inject
    lateinit var authenticationFeatureAPI: AuthenticationFeatureAPI

    @Inject
    lateinit var hasAnySessionUseCase: HasAnySessionUseCase

    @Inject
    lateinit var bottomNavBarAPI: BottomNavBarAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false}
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            val scope = CoroutineScope(Dispatchers.IO)
            AflamiTheme {
                scope.launch {
                    when {
                        hasAnySessionUseCase() -> bottomNavBarAPI()
                        isOnboardingCompletedUseCase() -> authenticationFeatureAPI()
                        else -> onBoardingApI()
                    }
                }

            }
        }
    }



}
