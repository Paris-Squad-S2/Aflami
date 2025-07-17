package com.feature.home.homeUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeFeatureAPI

class HomeFeatureAPIImpl : HomeFeatureAPI {
    override fun invoke(homeDestination: HomeDestination?): @Composable (() -> Unit) {
        return {
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}