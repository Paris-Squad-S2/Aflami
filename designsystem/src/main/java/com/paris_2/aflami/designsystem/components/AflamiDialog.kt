package com.paris_2.aflami.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AflamiDialog(
    onDismiss: () -> Unit,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surface, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = title),
                        style = Theme.textStyle.title.large,
                        color = Theme.colors.text.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_cancel),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Theme.colors.surfaceHigh)
                            .clickable { onDismiss() }
                            .padding(10.dp),
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = Theme.colors.text.title
                    )
                }
                content()
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    )
}

@PreviewLightDark
@Composable
fun PreviewAflamiDialog() {
    BasePreview {
        var showDialog = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AflamiButton(
                text = R.string.click_me,
                onClick = { showDialog.value = true },
                type = ButtonType.Primary,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        if (showDialog.value) {
            AflamiDialog(
                onDismiss = { showDialog.value = false },
                title = R.string.settings,
            ) {
                Column {
                    AflamiButton(
                        text = R.string.settings,
                        onClick = { },
                        type = ButtonType.Primary,
                        state = ButtonState.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    AflamiButton(
                        text = R.string.cancel,
                        onClick = { showDialog.value = false },
                        type = ButtonType.Secondary,
                        state = ButtonState.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}