package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    titleTextStyle: TextStyle = if (subtitle != null) Theme.textStyle.label.medium else Theme.textStyle.title.large,
    logo: IconItem? = null,
    leadingIcons: List<IconItem> = emptyList(),
    trailingIcons: List<IconItem> = emptyList(),
    trailingContent: (@Composable () -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val scrollModifier = scrollBehavior?.let {
        Modifier.nestedScroll(it.nestedScrollConnection)
    } ?: Modifier

    Box(
        modifier = modifier
            .then(scrollModifier)
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp),
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
                IconBox(it)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                title?.let {
                    AppText(
                        text = it,
                        style = titleTextStyle,
                        color = Theme.colors.text.title,
                        maxLines = 1,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
                subtitle?.let {
                    AppText(
                        text = it,
                        style = Theme.textStyle.label.small,
                        color = Theme.colors.text.body,
                        maxLines = 1,
                        lineHeight = 16.sp
                    )
                }
            }

            trailingIcons.forEachIndexed { index, iconItem ->
                Spacer(modifier = Modifier.width(if (index > 0) 8.dp else 0.dp))
                IconBox(iconItem)
            }

            trailingContent?.invoke()
        }
    }
}

@Composable
private fun IconBox(
    iconItem: IconItem,
    modifier: Modifier = Modifier,
    tint: Color = iconItem.tint,
) {
    Box(
        modifier = modifier
            .size(40.dp)
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
        AppIcon(
            imageVector = iconItem.icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .size(24.dp)
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
        AppTopBar(
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

        AppTopBar(
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

        AppTopBar(
            title = "My Account",
            trailingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(R.drawable.ic_sort),
                )
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        AppTopBar(title = "Settings")

        Spacer(modifier = Modifier.height(8.dp))

        AppTopBar(
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
