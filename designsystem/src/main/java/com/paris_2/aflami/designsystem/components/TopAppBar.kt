package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

data class IconItem(
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null,
    val backgroundColor: Color,
    val tint: Color,
)

@Composable
fun iconItemWithDefaults(
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = Theme.colors.surfaceHigh,
    tint: Color = Theme.colors.text.title,
): IconItem {
    return IconItem(
        icon = icon,
        onClick = onClick,
        backgroundColor = backgroundColor,
        tint = tint
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    logo: IconItem? = null,
    leadingIcons: List<IconItem> = emptyList(),
    trailingIcons: List<IconItem> = emptyList(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val scrollModifier = scrollBehavior?.let {
        Modifier.nestedScroll(it.nestedScrollConnection)
    } ?: Modifier

    Box(
        modifier = modifier
            .then(scrollModifier)
            .fillMaxWidth()
            .height(56.dp)
            .background(Theme.colors.surface)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            leadingIcons.forEach {
                IconBox(it)
            }

            logo?.let {
                IconBox(it, modifier = Modifier, tint = Color.Unspecified)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                title?.let {
                    Text(
                        text = it,
                        style = if (subtitle != null) Theme.textStyle.label.medium else Theme.textStyle.title.large,
                        color = Theme.colors.text.title,
                        maxLines = 1,
                    )
                }
                subtitle?.let {
                    Text(
                        text = it,
                        style = Theme.textStyle.label.small,
                        color = Theme.colors.text.body,
                        maxLines = 1,
                    )
                }
            }

            trailingIcons.forEachIndexed { index, iconItem ->
                Spacer(modifier = Modifier.width(if (index > 0) 8.dp else 0.dp))
                IconBox(iconItem)
            }
        }
    }
}

@Composable
private fun IconBox(
    iconItem: IconItem,
    tint: Color = iconItem.tint,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(40.dp)
//            .border(1.dp, color = Theme.colors.stroke, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(iconItem.backgroundColor)
            .then(
                if (iconItem.onClick != null) {
                    Modifier.clickable { iconItem.onClick.invoke() }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconItem.icon,
            contentDescription = null,
            tint = tint
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewMultiDevices
@Composable
fun PreviewTopAppBar() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Theme.colors.surface)
            .padding(bottom = 16.dp)
    ) {
        TopAppBar(
            title = "AFLAMI",
            subtitle = "More than just watching.",
            logo = iconItemWithDefaults(
                ImageVector.vectorResource(R.drawable.ic_aflami_logo), {},
                Theme.colors.primaryVariant,
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_search),
                    {},
                    Theme.colors.primaryVariant,
                    tint = Theme.colors.text.body
                ),
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TopAppBar(
            title = "My Account",
            leadingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_back),
                    {},
                )
            ),
            trailingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_sort),
                    {},
                )
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TopAppBar(
            title = "My Account",
            trailingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_sort),
                )
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TopAppBar(title = "Settings")

        Spacer(modifier = Modifier.height(8.dp))

        TopAppBar(
            title = "My Account",
            trailingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_star),
                    {},
                ),
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_sort),
                    {},
                )
            )
        )
    }
}
