package com.feature.home.homeUi

import androidx.compose.runtime.Composable
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.home.homeUi.screen.home.HomeScreen

class HomeFeatureAPIImpl() : HomeFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            HomeScreen()
        }
    }
}