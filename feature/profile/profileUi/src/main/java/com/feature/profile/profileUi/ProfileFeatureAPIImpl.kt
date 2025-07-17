package com.feature.profile.profileUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.internal.ComposableFunction0
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.profile.profileApi.ProfileDestination
import com.feature.profile.profileApi.ProfileFeatureAPI

class ProfileFeatureAPIImpl : ProfileFeatureAPI {
    override fun invoke(profileDestination: ProfileDestination?): @androidx.compose.runtime.Composable ComposableFunction0<Unit> {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Profile Feature",
                )
            }
        }
    }

}