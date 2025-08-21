package com.paris.aflami.designsystem.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.theme.Theme
import com.paris.aflami.designsystem.utils.BasePreview
import com.paris.aflami.designsystem.utils.PreviewMultiDevices

@Composable
fun CategoryCard(
    categoryName : String,
    categoryImage : Painter,
    onCategoryClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = with(LocalDensity){ LocalWindowInfo.current.containerSize.width.dp }
    val textPadding = if (screenWidth <= 1080.dp) 60.dp else 0.dp
    Box(
        modifier = modifier
            .height(79.dp)
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
                ).clickable{onCategoryClick()}
                .padding(start = 8.dp)
            ,
        ) {
            AppText(
                text = categoryName,
                style = Theme.textStyle.label.medium,
                color = Theme.colors.text.title,
                modifier = Modifier.fillMaxSize().padding(top = 12.dp,end = textPadding),
                overflow = TextOverflow.Ellipsis
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