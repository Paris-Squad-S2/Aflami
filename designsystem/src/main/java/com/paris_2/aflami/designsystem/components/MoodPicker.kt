package com.paris_2.aflami.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun MoodPicker(
    modifier: Modifier,
    backgroundColor: List<Color> ,
    image : Painter,
    onEmojiClick: (emojiMood: MoodType) -> Unit,
    title: String,
    question: String,
    ) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = backgroundColor,
                ),
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {


        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp)
                .background(
                    color = Theme.colors.onPrimaryColors.onPrimaryButton.copy(alpha = 0.08f),
                    shape = CircleShape
                )
                .border(0.5.dp, brush = heartIconContainerColor, CircleShape)
                .align(Alignment.TopStart),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_heart),
                contentDescription = "Icon heart",
                tint = Theme.colors.onPrimaryColors.onPrimary,
                modifier = Modifier.padding(5.dp)
            )
        }

        Text(
            text = title,
            style = Theme.textStyle.title.small,
            modifier = Modifier
                .padding(start = 12.dp, top = 44.dp)
                .align(Alignment.TopStart)
        )


        Image(
            painter = image,
            contentDescription = "Image clown",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .padding(top = 74.dp)
                .background(color = Theme.colors.surface, shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = question,
                style = Theme.textStyle.body.small,
            )


            val isEmojiSelected = remember { mutableStateOf(false) }
            val selectedEmojiIndex = remember { mutableIntStateOf(-1) }
            val selectedEmojiMood = remember { mutableStateOf(MoodType.SAD) }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                MoodPickerList.forEachIndexed { index, icon ->
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "Icons",
                        tint = if (selectedEmojiIndex.intValue == index) Theme.colors.primary else Theme.colors.text.body,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clip(CircleShape)
                            .clickable{
                                isEmojiSelected.value = true
                                selectedEmojiIndex.intValue = index
                                selectedEmojiMood.value = MoodType.entries[index]
                            }
                    )
                }
            }
            Text(
                text = "Get now",
                style = Theme.textStyle.label.medium,
                color = if (isEmojiSelected.value) Theme.colors.primary else Theme.colors.disable,
                modifier = Modifier.wrapContentSize().clickable{ onEmojiClick(selectedEmojiMood.value) }
            )
        }
    }
}

@Preview("Dark Theme")
@Composable
private fun MoodPickerDarkThemePreview() {
    AflamiTheme(isDarkTheme = true) {
        MoodPicker(
            title = "Mood Picker ( Get a Movie )",
            question = "What’s your vibe today? ",
            onEmojiClick = {emojiMood ->
                // action
            },
            image = painterResource(R.drawable.img_clown),
            backgroundColor = listOf(
                Theme.colors.primary,
                Theme.colors.status.redAccent,
                Theme.colors.status.yellowAccent,
            ),
            modifier = Modifier.size(328.dp, 194.dp)
        )
    }
}

@Preview("Light Theme")
@Composable
private fun MoodPickerLightThemePreview() {
    AflamiTheme(isDarkTheme = false) {
        MoodPicker(
            title = "Mood Picker ( Get a Movie )",
            question = "What’s your vibe today? ",
            onEmojiClick = {emojiMood ->
                // action
            },
            backgroundColor = listOf(
                Theme.colors.primary,
                Theme.colors.status.redAccent,
                Theme.colors.status.yellowAccent,
            ),
            image = painterResource(R.drawable.img_clown),
            modifier = Modifier.size(328.dp, 194.dp)
        )
    }
}

enum class MoodType(val tags: List<String>){
    SAD(listOf("Comedy")),
    NEUTRAL(listOf("Documentary","Mystery")),
    ROMANTIC(listOf("Romance","Musical")),
    ANGRY(listOf("Comedy","Animation","Family")),
    DEPRESSED(listOf("Drama","Animation")),
    SAD_DIZZY(listOf("Adventure","Fantasy","Sci-Fi"))
}

private val MoodPickerList = listOf(
    R.drawable.emoji_sad,
    R.drawable.emoji_look_top,
    R.drawable.emoji_in_love,
    R.drawable.emoji_angry,
    R.drawable.emoji_unhappy,
    R.drawable.emoji_sad_dizzy
)
private val heartIconContainerColor = Brush.linearGradient(
    colorStops = arrayOf(
        0.0f to Color.White.copy(alpha = 0.08f),
        0.8f to Color.White.copy(alpha = 0.08f),
        1.0f to Color.White.copy(alpha = 0.24f)
    )
)