package com.feature.profile.profileApi

import androidx.compose.runtime.Composable

interface ProfileFeatureAPI {
    operator fun invoke(): @Composable () -> Unit
}