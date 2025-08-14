package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.feature.guessGame.guessGameUi.common.components.GameTimer
import com.feature.guessGame.guessGameUi.common.components.OptionItem
import com.feature.guessGame.guessGameUi.common.components.QuestionIndicator
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.GuessCard
import com.paris_2.aflami.designsystem.components.GuessCardImageState
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.utils.BasePreview

@Composable
fun GuessByImageScreen(modifier: Modifier = Modifier) {
    GussByImageScreenContent(modifier = modifier.fillMaxWidth())
}

@Composable
private fun GussByImageScreenContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 12.dp)) {
        Header()
        QuestionIndicator(5, 2, modifier = Modifier.padding(vertical = 18.dp))
        QuestionImage()



        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) { answer ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    OptionItem(
                        text = "Ahmed",
                        selected = false,
                        isCorrect = false,
                        onClick = { }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            onClick = { },
            text = com.feature.guessGame.guessGameUi.R.string.next,
            type = ButtonType.Primary,
            state = ButtonState.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    AppTopBar(
        modifier = modifier,
        title = "Guess character",
        leadingIcons = listOf(
            iconItemWithDefaults(
                ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_cancel),
                {}
            )
        ),
        trailingContent = {
            GameTimer(
                totalSeconds = 45,
                onFinished = { }
            )
        }
    )

}

@Composable
fun QuestionImage(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(GuessCardImageState.Hard) }
    GuessCard(
        imagePainter = painterResource(R.drawable.img_guess_character),
        clickable = true,
        imageState = state,
        showHint = state == GuessCardImageState.Hard,
        onClick = {
            state = when (state) {
                GuessCardImageState.Hard -> GuessCardImageState.Medium
                GuessCardImageState.Medium -> GuessCardImageState.Show
                GuessCardImageState.Show -> GuessCardImageState.Hard
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun GuessByImagePrev() {
    BasePreview {
        GuessByImageScreen()
    }
}