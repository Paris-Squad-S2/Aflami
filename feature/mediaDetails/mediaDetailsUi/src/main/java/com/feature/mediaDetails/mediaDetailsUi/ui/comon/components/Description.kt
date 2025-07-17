package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun Description(
    description: String,
) {
    var expanded by remember { mutableStateOf(false) }
    val maxLines = if (expanded) Int.MAX_VALUE else 5

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Theme.colors.text.hint)) {
            append(description)
        }
        if (!expanded) {
            append(" ")
            withStyle(
                style = SpanStyle(
                    color = Theme.colors.primary,
                )
            ) {
                append("Read more")
            }
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colors.surface)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Description",
                style = Theme.textStyle.headline.small,
                color = Theme.colors.text.title
            )

            Spacer(modifier = Modifier.height(12.dp))

            ClickableText(
                text = annotatedText,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                style = Theme.textStyle.body.small,
                onClick = { expanded = true }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun DescriptionPreview() {
    Description(
        description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger"
    )
}