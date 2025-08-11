package com.paris_2.aflami.bottomNavBar.bottomNavBarUI.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Navigator
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.ui.main.InstallSavedAppLanguage
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.domain.user.usecase.SettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomNavBarActivity : AppCompatActivity() {

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
            InstallSavedAppLanguage(this)
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

}