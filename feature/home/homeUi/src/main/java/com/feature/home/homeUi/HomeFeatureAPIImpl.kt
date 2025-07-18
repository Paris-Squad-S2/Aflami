package com.feature.home.homeUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeFeatureAPI
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.ButtonType
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

class HomeFeatureAPIImpl(
    private val appNavigator: AppNavigator = getKoin().get()
) : HomeFeatureAPI {
    override fun invoke(homeDestination: HomeDestination?): @Composable (() -> Unit) {
        return {
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AflamiButton(
                    text = R.string.go_to_search,
                    type = ButtonType.Primary,
                    onClick = {
                        scope.launch {
                            appNavigator.navigate(
                                destination = AppDestinations.SearchFeature()
                            )
                        }
                    }
                )
            }
        }
    }
}