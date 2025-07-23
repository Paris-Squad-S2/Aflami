package com.feature.authentication.authenticationUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.authentication.authenticationApi.AuthenticationDestination
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.ButtonType
import kotlinx.coroutines.launch

class AuthenticationFeatureAPIImpl(
    private val appNavigator: AppNavigator
) : AuthenticationFeatureAPI {
    override fun invoke(authenticationDestination: AuthenticationDestination?): @Composable (() -> Unit) {
        return {
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    text = R.string.go_to_home,
                    type = ButtonType.Primary,
                    onClick = {
                        scope.launch {
                            appNavigator.navigate(
                                destination = AppDestinations.HomeFeature()
                            )
                        }
                    }
                )
            }
        }
    }
}