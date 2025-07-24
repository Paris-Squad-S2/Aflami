package com.feature.home.homeUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.search.searchApi.SearchFeatureAPI
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.ButtonType

class HomeFeatureAPIImpl(
    private val searchFeatureAPI: SearchFeatureAPI
) : HomeFeatureAPI {
    override fun invoke(homeDestination: HomeDestination?): @Composable (() -> Unit) {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    text = R.string.go_to_search,
                    type = ButtonType.Primary,
                    onClick = {
                        searchFeatureAPI()
                    }
                )
            }
        }
    }
}