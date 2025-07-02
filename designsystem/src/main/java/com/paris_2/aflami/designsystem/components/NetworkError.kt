package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun NetworkError(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .width(92.dp)
                .height(80.dp)
                .padding(bottom = 16.dp),
            painter = painterResource(R.drawable.ic_network_error),
            contentDescription = stringResource(R.string.network_error_icon),
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.network_error_message),
            style = Theme.textStyle.title.medium,
            color = Theme.colors.text.title
        )
        Text(
            text = stringResource(R.string.network_error_hint),
            style = Theme.textStyle.label.medium,
            color = Theme.colors.text.body,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        AflamiButton(
            text = R.string.retry,
            onClick = onRetry,
            type = ButtonType.Secondary,
            state = ButtonState.Normal,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

    }
}

@Composable
@PreviewLightDark
fun NetworkErrorPreview() {
    BasePreview {
        NetworkError(
            onRetry = {}
        )
    }
}

@Composable
@PreviewLightDark
fun NetworkErrorPreview2() {
    BasePreview {
        NetworkError(
            modifier = Modifier
                .fillMaxSize(),
            onRetry = {}
        )
    }
}