package com.feature.profile.profileUi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.ComposableFunction0
import com.feature.profile.profileUi.navigation.ProfileNavGraph
import com.feature.profile.profileApi.ProfileFeatureAPI

class ProfileFeatureAPIImpl : ProfileFeatureAPI {
    override fun invoke(): @Composable ComposableFunction0<Unit> {
        return {
            ProfileNavGraph()
        }
    }

}