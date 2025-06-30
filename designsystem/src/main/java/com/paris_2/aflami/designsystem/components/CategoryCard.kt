package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.Theme
import com.paris_2.aflami.designsystem.utils.BasePreview
import com.paris_2.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun CategoryCard(
    categoryName : String,
    categoryImage : Painter,
    onCategoryClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(79.dp)
            .clickable{onCategoryClick()}
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(71.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(16.dp))
                .background(Theme.colors.surfaceHigh)
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(start = 8.dp),
        ) {
            Text(
                text = categoryName,
                style = Theme.textStyle.label.medium,
                color = Theme.colors.text.title,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        Image(
            painter = categoryImage,
            contentDescription = "$categoryName category",
            modifier = Modifier
                .height(71.dp)
                .align(Alignment.TopEnd)
        )
    }
}


@Composable
@PreviewMultiDevices
fun CategoryCardPreview(){
    BasePreview {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryCard(
                categoryName = "Action",
                categoryImage = painterResource(R.drawable.img_category_card),
                onCategoryClick = {},
                modifier = Modifier.weight(1f)
            )
            CategoryCard(
                categoryName = "Action",
                categoryImage = painterResource(R.drawable.img_category_card),
                onCategoryClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}