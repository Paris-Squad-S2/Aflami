package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.components.AflamiButton
import com.paris_2.aflami.designsystem.components.AflamiDialog
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme


@Composable
fun LoginDialog(
    @StringRes title: Int,
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: Int = R.string.please_login_to_access_your_account_details_and_other_features,
    image: Painter = painterResource(R.drawable.profile_placholder),
    @StringRes buttonTextResId: Int = R.string.login,
) {
    AflamiDialog(
        onDismiss = onDismiss,
        title = title,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(
                        elevation = 12.dp, // reasonable shadow
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color(0xFFD85895),
                        spotColor = Color(0xFFD85895)
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White) // Required for shadow to appear
                    .border(
                        width = 1.dp,
                        color = Theme.colors.stroke.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = image,
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )
            }

            Text(
                text = stringResource(content),
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.body,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 24.dp)
            )
            AflamiButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                text = buttonTextResId,
                type = ButtonType.Secondary,
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
            title = R.string.rate,
            onDismiss = {},
            onLoginClick = {},
            buttonTextResId = R.string.login,
        )
    }
}