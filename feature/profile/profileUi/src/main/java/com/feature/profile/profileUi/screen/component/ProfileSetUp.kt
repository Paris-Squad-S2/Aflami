package com.feature.profile.profileUi.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.feature.profile.profileUi.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.AppText
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun ProfileSetUp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SetUpItem(
            onClick = {},
            title = "Language",
            icon = R.drawable.ic_translate
        )
        SetUpItem(
            onClick = {},
            title = "App Theme",
            icon = R.drawable.ic_moon
        )
        SetUpItem(
            onClick = {},
            title = "Setting",
            icon = R.drawable.ic_customize
        )
    }
}

@Composable
fun SetUpItem(modifier: Modifier = Modifier, icon: Int, title: String, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp, color = Theme.colors.stroke,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = Theme.colors.surfaceHigh,
                    shape = RoundedCornerShape(12.dp)
                )

                .padding(8.dp)
        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = Theme.colors.text.body,
            )
        }
        AppText(
            text = title,
            style = Theme.textStyle.label.large,
            color = Theme.colors.text.title,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1F)
        )
        AppText(
            text = "ENG",
            style = Theme.textStyle.label.small,
            color = Theme.colors.text.body,
            modifier = Modifier.padding(end = 8.dp)
        )

        AppIcon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = Theme.colors.text.hint,
        )


    }
}