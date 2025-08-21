package com.paris.aflami

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.paris.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.aflami.main.InstallSavedAppLanguage
import com.paris.domain.user.usecase.auth.HasAnySessionUseCase
import com.paris.domain.user.usecase.onboarding.IsOnboardingCompletedUseCase
import com.paris.domain.user.usecase.ManageSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    @Inject
    lateinit var manageSettingsUseCase: ManageSettingsUseCase

    private val minSplashMillis = 2833L
    @Volatile private var releaseSplash = false
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        startTime = SystemClock.uptimeMillis()

        splash.setKeepOnScreenCondition {
            val elapsed = SystemClock.uptimeMillis() - startTime
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                !releaseSplash && elapsed < minSplashMillis
            } else {
                !releaseSplash
            }
        }

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val remaining = minSplashMillis - (SystemClock.uptimeMillis() - startTime)
            if (remaining > 0) delay(remaining)
            releaseSplash = true
        }

        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(isDarkTheme = manageSettingsUseCase.isDarkTheme()) {
                LaunchedEffect(Unit) {
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