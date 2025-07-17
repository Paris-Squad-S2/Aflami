package com.paris_2.aflami.designsystem.components


import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    androidx.compose.material3.Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}

@Composable
@Preview
fun IconPreview() {
    Icon(
        imageVector = ImageVector.vectorResource(id = com.paris_2.aflami.designsystem.R.drawable.ic_launcher_foreground),
        contentDescription = "Preview Icon",
        modifier = Modifier,
        tint = Color.Unspecified
    )
}