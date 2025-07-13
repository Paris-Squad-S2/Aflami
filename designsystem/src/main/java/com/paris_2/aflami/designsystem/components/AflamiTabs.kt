package com.paris_2.aflami.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun CustomTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.text.title else Theme.colors.text.hint,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "TabTextColorAnimation"
    )
    Box(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    ) {
        Text(
            text = title,
            color = animatedColor,
            style = Theme.textStyle.label.medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun TabRow(
    tabItems: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.stroke)
            .padding(bottom = 1.dp)
            .background(Theme.colors.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            tabItems.forEachIndexed { index, title ->
                CustomTab(
                    title = title,
                    isSelected = index == selectedIndex,
                    onClick = { onTabSelected(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = animateDpAsState(
                        targetValue = (selectedIndex * (LocalConfiguration.current.screenWidthDp / tabItems.size).dp)
                            .coerceAtLeast(0.dp),
                        label = "TabOffsetAnimation",
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    ).value
                )
                .fillMaxWidth(1f - 1f / tabItems.size)
                .padding(horizontal = 24.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Theme.colors.secondary)
        )
    }
}

@PreviewMultiDevices
@Composable
fun TabDesignPreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    BasePreview {
        TabRow(
            tabItems = listOf("All", "Some"),
            selectedIndex = selectedIndex,
            onTabSelected = {
                selectedIndex = it
            }
        )
    }
}