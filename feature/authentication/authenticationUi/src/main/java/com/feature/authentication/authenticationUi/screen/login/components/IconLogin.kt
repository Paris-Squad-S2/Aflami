package com.feature.authentication.authenticationUi.screen.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconLogin(modifier: Modifier = Modifier) {

    Image(
        painter = painterResource(id = com.paris_2.aflami.designsystem.R.drawable.play_media),
        contentDescription = null,
        modifier = modifier
            .width(25.dp)
            .height(28.dp)
            .offset(x = 4.dp)
    )
}
