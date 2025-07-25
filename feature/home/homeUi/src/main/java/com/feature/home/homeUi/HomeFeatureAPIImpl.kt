package com.feature.home.homeUi

import androidx.compose.runtime.Composable
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.home.homeUi.navigation.HomeNavGraph

class HomeFeatureAPIImpl() : HomeFeatureAPI {
    override fun invoke(homeDestination: HomeDestination?): @Composable (() -> Unit) {
        return {
            HomeNavGraph(startDestination = homeDestination)
        }
    }
}