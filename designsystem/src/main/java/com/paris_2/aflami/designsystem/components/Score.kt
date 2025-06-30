package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun Score(
    score: String,
    isTrue: Boolean,
    modifier: Modifier = Modifier
){
    val backgroundColor = if (isTrue) Theme.colors.status.greenVariant else Theme.colors.status.redVariant
    val textColor = if (isTrue) Theme.colors.status.greenAccent else Theme.colors.status.redAccent
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            text = score,
            color = textColor,
            style = Theme.textStyle.label.large
        )
    }
}

@Composable
@PreviewMultiDevices
fun ScorePreview() {
    BasePreview {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Score(score = "-1", isTrue = false)
            Score(score = "+1", isTrue = true)
        }
    }
}
