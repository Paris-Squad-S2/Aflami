package com.feature.profile.profileUi.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.feature.profile.profileUi.screen.component.ProfileDetails
import com.feature.profile.profileUi.screen.component.ProfileHeader
import com.feature.profile.profileUi.screen.component.ProfileSetUp

import com.paris_2.aflami.designsystem.components.CategoryCard
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            CategoryCard(
                "Watch history",
                painterResource(R.drawable.ic_clock_3d),
                onCategoryClick = { },
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CategoryCard(
                "My rating",
                painterResource(R.drawable.ic_star_3d),
                onCategoryClick = { },
                modifier = Modifier.weight(1F)
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Theme.colors.stroke,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        ProfileSetUp()
    }
}