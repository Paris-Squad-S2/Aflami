package com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.descriptionSection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun Description(description: String) {
    DescriptionCard {
        Column {
            DescriptionTitle()
            Spacer(modifier = Modifier.height(12.dp))
            ExpandableText(description = description)
        }
    }
}

@Composable
fun DescriptionTitle() {
    Text(
        text = "Description",
        style = Theme.textStyle.headline.small,
        color = Theme.colors.text.title
    )
}

@PreviewLightDark
@Composable
fun DescriptionPreview() {
    Description(
        description = "In 1935, corrections officer Paul Edgecomb oversees \"The Green Mile,\" the death row section of Cold Mountain Penitentiary, alongside officers Brutus Howell, Dean Stanton, Harry Terwilliger. When John Coffey, a giant African-American man convicted of brutally murdering two little white girls, arrives on death row, Paul begins to notice something unusual about him. Coffey seems to possess a supernatural power to heal people's ailments. As Paul and his fellow officers investigate further, they discover that Coffey may be innocent of the crimes he was convicted of. The story explores themes of justice, redemption, and the supernatural within the confines of a Depression-era prison."
    )
}