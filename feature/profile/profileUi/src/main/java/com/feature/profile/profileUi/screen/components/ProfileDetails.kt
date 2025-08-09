package com.feature.profile.profileUi.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ProfileDetails(modifier: Modifier = Modifier, username: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_profile_image),
            contentDescription = null,
        )
        AppText(
            text = username,
            style = Theme.textStyle.label.medium,
            color = Theme.colors.text.body,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(colors = Theme.colors.gradient.pointsOverly),
                    shape = RoundedCornerShape(100)
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(
                text = "325 Pts.",
                style = Theme.textStyle.label.small,
                color = Theme.colors.onPrimaryColors.onPrimary
            )
            Spacer(Modifier.width(4.dp))
            AppIcon(
                imageVector = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_horisontal_star),
                contentDescription = null,
                tint = Theme.colors.onPrimaryColors.onPrimary
            )
        }
    }
}