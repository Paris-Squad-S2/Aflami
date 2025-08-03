package com.feature.profile.profileUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.ComposableFunction0
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.feature.profile.profileUi.screen.ProfileScreen

class ProfileFeatureAPIImpl : ProfileFeatureAPI {
    override fun invoke(): @Composable ComposableFunction0<Unit> {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProfileScreen()
            }
        }
    }

}