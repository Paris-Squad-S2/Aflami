package com.feature.profile.profileUi.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.component.ProfileDetails
import com.feature.profile.profileUi.screen.component.ProfileHeader

import com.paris_2.aflami.designsystem.components.CategoryCard


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileContent(modifier = modifier)
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileHeader()
        ProfileDetails()
        Row {
            CategoryCard(
                "Watch history",
                painterResource(R.drawable.ic_clock_3d),
                onCategoryClick = { },

                )
            CategoryCard(
                "Watch history",
                painterResource(R.drawable.ic_star_3d),
                onCategoryClick = { },

                )

        }
    }
}