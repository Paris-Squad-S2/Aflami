package com.paris_2.aflami

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.user.usecase.IsOnboardingCompletedUseCase
import com.paris_2.domain.user.usecase.HasAnySessionUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                when {
                    hasAnySessionUseCase() -> bottomNavBarAPI()
                    isOnboardingCompletedUseCase() -> authenticationFeatureAPI()
                    else -> onBoardingApI()
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("settings", MODE_PRIVATE)
        val lang = prefs.getString("language_code", "en") ?: "en"
        val localizedContext = updateLocale(newBase, lang)
        super.attachBaseContext(localizedContext)
    }

    private fun updateLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
            when {
                hasAnySessionUseCase() -> bottomNavBarAPI()
                isOnboardingCompletedUseCase() -> authenticationFeatureAPI()
                else -> onBoardingApI()
            }
        }) as Context
    }
}
