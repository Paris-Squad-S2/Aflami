package com.paris_2.aflami.bottomNavBar.bottomNavBarUI.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Navigator
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class BottomNavBarActivity : ComponentActivity() {

    @Inject
    lateinit var bottomNavBarNavigator: Navigator

    @Inject
    lateinit var homeFeature: HomeFeatureAPI

    @Inject
    lateinit var listsFeature: ListsFeatureAPI

    @Inject
    lateinit var categoriesFeature: CategoriesFeatureAPI

    @Inject
    lateinit var letsPlayFeature: GuessGameFeatureAPI

    @Inject
    lateinit var profileFeature: ProfileFeatureAPI

    @Inject
    lateinit var settingsUseCase: SettingsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme(settingsUseCase.isDarkTheme()) {
                BottomNavBarScaffold(
                    navigator = bottomNavBarNavigator,
                    homeFeature = homeFeature,
                    listsFeature = listsFeature,
                    categoriesFeature = categoriesFeature,
                    letsPlayFeature = letsPlayFeature,
                    profileFeature = profileFeature,
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