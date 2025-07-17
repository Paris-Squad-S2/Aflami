package com.feature.profile.profileUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.ComposableFunction0
import androidx.compose.ui.Modifier
import com.feature.lists.listsApi.ListsDestination
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.profile.profileApi.ProfileDestination
import com.feature.profile.profileApi.ProfileFeatureAPI

class ProfileFeatureAPIImpl : ProfileFeatureAPI {
    override fun invoke(profileDestination: ProfileDestination?): @androidx.compose.runtime.Composable ComposableFunction0<Unit> {
        return {
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }
    }

}