package com.paris.aflami.designsystem.components

import androidx.annotation.StringRes
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.theme.Theme
import com.paris.aflami.designsystem.utils.BasePreview
import com.paris.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun AppSnackBar(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    isSuccess: Boolean,
    onClick: () -> Unit = {}
) {
    val icon = if (isSuccess) R.drawable.ic_thumbs_up else R.drawable.ic_thumbs_down
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
        AppIcon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = if (isSuccess) stringResource(R.string.thumbs_up_icon) else stringResource(
                R.string.thumbs_down_icon
            ),
            tint = color,
        )
        AppText(
            text = stringResource(id = text),
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
            AppSnackBar(
                text = R.string.some_error_happened,
                isSuccess = false
            )
            Spacer(modifier = Modifier.height(50.dp))
            AppSnackBar(
                text = R.string.added_new_list_successfully,
                isSuccess = true
            )
        }
    }
}