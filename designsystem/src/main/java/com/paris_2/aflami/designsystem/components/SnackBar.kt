package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    text: String,
    isSuccess: Boolean,
    onClick: () -> Unit = {}
) {
    val icon = if (isSuccess) painterResource(id = R.drawable.ic_thumbs_up)
    else painterResource(id = R.drawable.ic_thumbs_down)
    val color = if (isSuccess) Theme.colors.status.greenAccent else Theme.colors.status.redAccent
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 16.dp,
                RoundedCornerShape(16.dp),
                ambientColor = color,
                spotColor = color,
                clip = true,
            )
            .background(Theme.colors.surfaceHigh)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon,
            contentDescription = if (isSuccess) stringResource(R.string.thumbs_up_icon) else stringResource(
                R.string.thumbs_down_icon
            ),
            tint = color,
        )
        Text(
            text = text,
            style = Theme.textStyle.body.medium,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}


@PreviewMultiDevices()
@Composable
private fun SnackBarErrorPreview() {
    BasePreview {
        Column(Modifier.padding(50.dp)) {
            SnackBar(
                text = "Some error happened.",
                isSuccess = false
            )
            Spacer(modifier = Modifier.height(50.dp))
            SnackBar(
                text = "Added new list successfully.",
                isSuccess = true
            )
        }
    }
}