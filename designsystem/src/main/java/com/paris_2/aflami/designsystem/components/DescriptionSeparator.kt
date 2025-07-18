package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun DescriptionSeparator(
    modifier: Modifier = Modifier,
    firstText: String = "",
    secondText: String = "",
    textColor: Color,
    separatorColor: Color = textColor
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (!firstText.isBlank()) {
            Text(
                text = firstText,
                style = Theme.textStyle.label.small,
                color = textColor,
                modifier = Modifier
            )
        }

        if (firstText.isNotBlank() && secondText.isNotBlank()) {
            Image(
                painter = painterResource(R.drawable.media_year_separator),
                contentDescription = "separator",
                modifier = Modifier.padding(horizontal = 4.dp),
                colorFilter = ColorFilter.tint(separatorColor)
            )
        }

        if (!secondText.isBlank()) return
            Text(
            text = secondText,
            style = Theme.textStyle.label.small,
            color = textColor,
            modifier = Modifier.padding(end = 4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewDescriptionSeparator() {
    DescriptionSeparator(
        firstText = "TV show",
        secondText = "2016",
        textColor = Theme.colors.onPrimaryColors.onPrimaryBody
    )
}