package com.feature.profile.profileUi.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.component.ProfileDetails
import com.feature.profile.profileUi.screen.component.ProfileHeader
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileContent(modifier = modifier)
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileHeader()
        ProfileDetails()
    }
}