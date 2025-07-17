package com.feature.home.homeUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeFeatureAPI

class HomeFeatureAPIImpl : HomeFeatureAPI {
    override fun invoke(homeDestination: HomeDestination?): @Composable (() -> Unit) {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Home Feature",
                )
            }
        }
    }
}