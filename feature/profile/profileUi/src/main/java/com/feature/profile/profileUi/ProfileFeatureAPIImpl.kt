package com.feature.profile.profileUi

import androidx.compose.runtime.Composable
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.feature.profile.profileUi.screen.profile.ProfileScreen

class ProfileFeatureAPIImpl : ProfileFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            ProfileScreen()
        }
    }

}