package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun SearchSuggestionHub(
    title : String,
    description : String,
    icon : Painter,
    gradientColors: List<Color>,
    onCardClick : () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .height(98.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(colors = gradientColors)
            )
            .clickable { onCardClick }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopEnd)
                .offset(x = 8.dp, y = (-5).dp)
                .blur(radius = 32.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .clip(CircleShape)
                .background(Theme.colors.onPrimaryColors.onPrimary.copy(alpha = .25f))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 10.dp)
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .padding(bottom = 4.dp)
            )
            Text(
                text = title,
                color = Theme.colors.onPrimaryColors.onPrimary,
                style = Theme.textStyle.title.small,
            )
            Text(
                text = description,
                color = Theme.colors.onPrimaryColors.onPrimaryBody,
                style = Theme.textStyle.label.small,
            )
        }
    }
}


@Composable
@PreviewMultiDevices
fun SearchSuggestionHubPreview() {
    BasePreview {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchSuggestionHub(
                title = "World Tour",
                description = "Explore World Cinema",
                icon = painterResource(id = R.drawable.img_world_tour),
                gradientColors = Theme.colors.gradient.pinkGradient,
                onCardClick = {},
                modifier = Modifier.weight(1f)
            )
            SearchSuggestionHub(
                title = "Find by Actor",
                description = "Search by favorite Actor",
                icon = painterResource(id = R.drawable.img_world_tour),
                gradientColors = Theme.colors.gradient.blueGradient,
                onCardClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}