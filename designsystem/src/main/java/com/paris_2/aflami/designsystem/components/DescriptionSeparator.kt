package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun DescriptionSeparator(
    modifier: Modifier = Modifier,
    texts: List<String> = emptyList(),
    textColor: Color,
    separatorColor: Color = textColor
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        texts.filter { it.isNotBlank() }.forEachIndexed { index, text ->
            if (index > 0) {
                Image(
                    painter = painterResource(R.drawable.media_year_separator),
                    contentDescription = "separator",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colorFilter = ColorFilter.tint(separatorColor)
                )
            }
            Text(
                text = text,
                style = Theme.textStyle.label.small,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = if (index == texts.lastIndex) Modifier.padding(end = 4.dp) else Modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewDescriptionSeparator1() {
    BasePreview {
        DescriptionSeparator(
            texts = listOf("TV show", "2016"),
            textColor = Theme.colors.text.hint
        )
    }
}

@Preview
@Composable
fun PreviewDescriptionSeparator2() {
    BasePreview {
        DescriptionSeparator(
            texts = listOf(),
            textColor = Theme.colors.text.hint
        )
    }
}

@Preview
@Composable
fun PreviewDescriptionSeparator3() {
    BasePreview {
        DescriptionSeparator(
            texts = listOf("Action", "Adventure", "Drama"),
            textColor = Theme.colors.text.hint
        )
    }
}

@Preview
@Composable
fun PreviewDescriptionSeparator4() {
    BasePreview {
        DescriptionSeparator(
            texts = listOf("Action", "", "Drama"),
            textColor = Theme.colors.text.hint
        )
    }
}

@Preview
@Composable
fun PreviewDescriptionSeparator5() {
    BasePreview {
        DescriptionSeparator(
            texts = listOf("Action", "", ""),
            textColor = Theme.colors.text.hint
        )
    }
}