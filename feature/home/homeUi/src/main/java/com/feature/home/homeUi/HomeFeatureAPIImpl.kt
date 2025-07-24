package com.feature.home.homeUi

import androidx.compose.runtime.Composable
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.home.homeUi.navigation.HomeNavGraph
import com.paris_2.aflami.appnavigation.AppNavigator
import org.koin.mp.KoinPlatform.getKoin

class HomeFeatureAPIImpl(
    private val appNavigator: AppNavigator = getKoin().get()
) : HomeFeatureAPI {
    override fun invoke(homeDestination: HomeDestination?): @Composable (() -> Unit) {
        return {
            HomeNavGraph(startDestination = homeDestination)
        }
    }
}