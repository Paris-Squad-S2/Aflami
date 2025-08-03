package com.feature.profile.profileUi.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.profile.profileUi.screen.component.ProfileBackGround


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileContent(modifier = modifier)
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileBackGround()
        Column {

        }
    }
}