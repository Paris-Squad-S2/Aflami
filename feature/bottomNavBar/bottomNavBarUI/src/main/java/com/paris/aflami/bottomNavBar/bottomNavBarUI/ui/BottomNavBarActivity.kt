package com.paris.aflami.bottomNavBar.bottomNavBarUI.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.paris.aflami.bottomNavBar.bottomNavBarUI.navigation.Navigator
import com.paris.aflami.bottomNavBar.bottomNavBarUI.ui.main.InstallSavedAppLanguage
import com.paris.aflami.designsystem.theme.AflamiTheme
import com.paris.domain.user.usecase.ManageSettingsUseCase
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
    lateinit var manageSettingsUseCase: ManageSettingsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstallSavedAppLanguage(this)
            AflamiTheme(manageSettingsUseCase.isDarkTheme()) {
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