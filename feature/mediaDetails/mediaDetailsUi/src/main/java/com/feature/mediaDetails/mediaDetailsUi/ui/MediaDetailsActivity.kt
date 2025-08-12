package com.feature.mediaDetails.mediaDetailsUi.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavGraph
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.fromJsonToMediaDetailsDestination
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MediaDetailsActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationFeatureAPI: AuthenticationFeatureAPI

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    @Inject
    lateinit var navigator: MediaDetailsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mediaDetailsDestination = intent.getStringExtra("mediaDetailsDestination")
            ?.fromJsonToMediaDetailsDestination()
            ?: MediaDetailsDestinations.MovieDetailsScreen(0)


        setContent {
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                MediaDetailsNavGraph(
                    navigator = navigator,
                    authenticationFeatureAPI = authenticationFeatureAPI,
                    mediaDetailsDestination = mediaDetailsDestination
                )
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

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}