package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun DescriptionCard(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colors.surface)
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun ExpandableText(description: String) {
    var expanded by remember { mutableStateOf(false) }
    var shouldShowReadMore by remember { mutableStateOf(false) }
    var truncatedText by remember { mutableStateOf("") }

    val maxLines = if (expanded) Int.MAX_VALUE else 5

    val displayText = if (expanded) {
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Theme.colors.text.hint)) {
                append(description)
            }
            append(" ")
            withStyle(style = SpanStyle(color = Theme.colors.primary)) {
                append("Read less")
            }
        }
    } else {
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Theme.colors.text.hint)) {
                append(if (shouldShowReadMore) truncatedText else description)
            }
            if (shouldShowReadMore) {
                append(" ")
                withStyle(style = SpanStyle(color = Theme.colors.primary)) {
                    append(stringResource(R.string.read_more))
                }
            }
        }
    }

    Text(
        text = displayText,
        maxLines = maxLines,
        overflow = TextOverflow.Visible,
        style = Theme.textStyle.body.small,
        onTextLayout = { textLayoutResult ->
            if (!expanded && !shouldShowReadMore) {
                val hasOverflow = textLayoutResult.hasVisualOverflow
                if (hasOverflow) {
                    shouldShowReadMore = true
                    val lastCharIndex = textLayoutResult.getLineEnd(maxLines - 1, visibleEnd = true)
                    val availableText = description.substring(0, lastCharIndex)

                    val readMoreSpace = 10
                    val safeEndIndex = maxOf(0, availableText.length - readMoreSpace)
                    var finalText = availableText.substring(0, safeEndIndex)
                    val lastSpaceIndex = finalText.lastIndexOf(' ')

                    if (lastSpaceIndex > 0) {
                        finalText = finalText.substring(0, lastSpaceIndex)
                    }

                    truncatedText = finalText.trimEnd()
                }
            }
        },
        modifier = Modifier
            .clickable(
                enabled = shouldShowReadMore,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                expanded = !expanded
            }
            .animateContentSize(animationSpec = tween(durationMillis = 200))
    )
}