package com.paris_2.aflami.appnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppNavigation : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AflamiTheme {
                AppScaffold(
                    appNavigator = appNavigator,
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