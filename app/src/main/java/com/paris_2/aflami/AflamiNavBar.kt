package com.paris_2.aflami

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.appnavigation.AppDestination
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AflamiText
import com.paris_2.aflami.designsystem.components.Icon
import com.paris_2.aflami.designsystem.components.NavigationBar
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun AflamiNavBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    destinations: List<AflamiNavBarItem> = AflamiNavBarItem.destinations,
    onItemClick: (Int) -> Unit = {},
) {
    val strokeColor = Theme.colors.stroke

    NavigationBar(
        containerColor = Theme.colors.surface,
        modifier = modifier.drawWithContent {
            drawContent()
            drawLine(
                color = strokeColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
    ) {
        destinations.forEachIndexed { index, item ->
            AflamiNavBarItem(
                currentIndex = index,
                selectedDestinationIndex = selectedIndex,
                currentItem = item,
                onItemClick = {
                    onItemClick(index)
                }
            )
        }
    }
}

@Composable
private fun RowScope.AflamiNavBarItem(
    currentIndex: Int,
    selectedDestinationIndex: Int,
    currentItem: AflamiNavBarItem,
    onItemClick: () -> Unit
) {
    val selected = selectedDestinationIndex == currentIndex

    Box(
        modifier = Modifier
            .weight(1f)
            .clickable(
                enabled = !selected,
                onClick = onItemClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AflamiNavBarIcon(
                currentItem = currentItem,
                selected = selected
            )
            AflamiText(
                text = stringResource(currentItem.label),
                style = Theme.textStyle.label.small,
                color = animateColorAsState(
                    targetValue = if (selected) Theme.colors.text.body else Theme.colors.text.hint,
                    label = "NavBarItemTextColor"
                ).value,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AflamiNavBarIcon(
    currentItem: AflamiNavBarItem,
    selected: Boolean = false
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = if (selected) Theme.colors.primaryVariant else Color.Unspecified,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        val animatedTint by animateColorAsState(
            targetValue = if (selected) Theme.colors.primary else Theme.colors.text.hint,
            label = "NavBarIconTint"
        )
        Icon(
            imageVector = ImageVector.vectorResource(currentItem.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = animatedTint
        )
    }
}

sealed class AflamiNavBarItem(
    @param:DrawableRes val icon: Int,
    val label: Int,
    val destination: AppDestination
) {
    data object Home : AflamiNavBarItem(
        icon = R.drawable.ic_home,
        label = R.string.home,
        destination = AppDestinations.HomeFeature()
    )

    data object Lists : AflamiNavBarItem(
        icon = R.drawable.ic_lists,
        label = R.string.lists,
        destination = AppDestinations.ListsFeature()
    )

    data object Categories : AflamiNavBarItem(
        icon = R.drawable.ic_categories,
        label = R.string.categories,
        destination = AppDestinations.CategoriesFeature()
    )

    data object LetsPlay : AflamiNavBarItem(
        icon = R.drawable.ic_play,
        label = R.string.let_s_play,
        destination = AppDestinations.LetsPlayFeature()
    )

    data object Profile : AflamiNavBarItem(
        icon = R.drawable.ic_profile,
        label = R.string.profile,
        destination = AppDestinations.ProfileFeature()
    )

    companion object {
        val destinations = listOf(Home, Lists, Categories, LetsPlay, Profile)
    }
}

@Composable
@PreviewMultiDevices
private fun AflamiNavigationBarPreview() {
    val selectedIndex by remember { mutableIntStateOf(0) }
    BasePreview {
        AflamiNavBar(
            selectedIndex = selectedIndex,
            onItemClick = { }
        )
    }
}