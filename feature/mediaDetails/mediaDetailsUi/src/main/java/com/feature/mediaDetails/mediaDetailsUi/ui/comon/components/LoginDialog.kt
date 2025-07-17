package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType


@Composable
fun LoginDialog(
    modifier: Modifier = Modifier,
    title: String,
    content : String,
    image : Painter,
    @StringRes buttonTextResId : Int,
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colors.surface)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.title.large,
                    color = Theme.colors.text.title,
                    textAlign = TextAlign.Start
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12))
                        .size(40.dp)
                        .background(Theme.colors.surfaceHigh)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cancel),
                        contentDescription = "Close",
                        tint = Theme.colors.text.title
                    )
                }
            }
            Icon(
                painter = image,
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Theme.colors.primary)
                    .border(
                        width = 1.dp,
                        color = Theme.colors.stroke.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .shadow(
                        elevation = 12.dp,
                        spotColor = Color(0xFFD85895).copy(alpha = 0.24f),
                        ambientColor = Color(0xFFD85895).copy(alpha = 0.24f),
                        shape = RoundedCornerShape(24.dp)
                    )
            )
            Text(
                text = content,
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.body,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
            AflamiButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                text = buttonTextResId,
                type = ButtonType.Primary,
                state = ButtonState.Normal
            )
        }
    }
}

@PreviewLightDark
@Composable
fun LoginRequiredDialogPreview() {
    AflamiTheme {
        LoginDialog(
            title = "Rate",
            content = "Please login to access your account details and other features!",
            image = painterResource(R.drawable.ic_aflami_logo),
            onDismiss = {},
            onLoginClick = {},
            buttonTextResId = R.string.login,
        )
    }
}