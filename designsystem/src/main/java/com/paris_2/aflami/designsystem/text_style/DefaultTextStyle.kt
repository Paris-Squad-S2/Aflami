package com.paris_2.aflami.designsystem.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.paris_2.aflami.designsystem.color.Colors

val defaultTextStyle = AflamiTextStyle(
    headline = SizedTextStyle(
        large = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 42.sp
        ),
        medium = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 36.sp
        ),
        small = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 30.sp
        )
    ),
    title = SizedTextStyle(
        large = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 30.sp
        ),
        medium = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 28.sp
        ),
        small = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    ),
    body = SizedTextStyle(
        large = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 28.sp
        ),
        medium = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        small = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    ),
    label = SizedTextStyle(
        large = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        medium = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 22.sp
        ),
        small = TextStyle(
            fontFamily = popPins,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 16.sp
        )
    )
)

fun generateAflamiTextStyle(colors: Colors): AflamiTextStyle {
    return defaultTextStyle.copy(
        headline = defaultTextStyle.headline.copy(
            large = defaultTextStyle.headline.large.copy(color = colors.text.title),
            medium = defaultTextStyle.headline.medium.copy(color = colors.text.title),
            small = defaultTextStyle.headline.small.copy(color = colors.text.title)
        ),
        title = defaultTextStyle.title.copy(
            large = defaultTextStyle.title.large.copy(color = colors.text.title),
            medium = defaultTextStyle.title.medium.copy(color = colors.text.title),
            small = defaultTextStyle.title.small.copy(color = colors.text.title)
        ),
        body = defaultTextStyle.body.copy(
            large = defaultTextStyle.body.large.copy(color = colors.text.body),
            medium = defaultTextStyle.body.medium.copy(color = colors.text.body),
            small = defaultTextStyle.body.small.copy(color = colors.text.body)
        ),
        label = defaultTextStyle.label.copy(
            large = defaultTextStyle.label.large.copy(color = colors.text.hint),
            medium = defaultTextStyle.label.medium.copy(color = colors.text.hint),
            small = defaultTextStyle.label.small.copy(color = colors.text.hint)
        )
    )
}