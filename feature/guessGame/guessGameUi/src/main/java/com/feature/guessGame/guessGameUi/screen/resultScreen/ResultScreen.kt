package com.feature.guessGame.guessGameUi.screen.resultScreen

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.common.components.GameResultCard
import com.feature.guessGame.guessGameUi.common.components.GuessGameBackground
import com.feature.guessGame.guessGameUi.common.components.WinCard
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.getTitleResId
import com.paris.aflami.designsystem.components.AppTopBar
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.components.iconItemWithDefaults
import com.paris.aflami.designsystem.theme.AflamiTheme

@Composable
fun ResultScreen(
    totalGameTime: Int,
    totalGamePoints: Int,
    gameType: QuestionType,
    viewModel: ResultViewModel = hiltViewModel(),

    ) {
    GuessGameBackground {
        ResultScreenContent(
            listener = viewModel,
            totalGameTime = totalGameTime,
            totalGamePoints = totalGamePoints,
            gameType = gameType
        )
    }
}

@Composable
fun ResultScreenContent(
    totalGameTime: Int,
    totalGamePoints: Int,
    gameType: QuestionType,
    listener: ResultInteractionListener,

    ) {
    val activity = LocalActivity.current
    Column(
        modifier = Modifier
            .statusBarsPadding()
    ) {
        AppTopBar(
            title = stringResource(id = gameType.getTitleResId()),
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(com.paris.aflami.designsystem.R.drawable.ic_cancel),
                    onClick = { activity?.finish() },
                )
            ),
        )
        WinCard(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp, bottom = 24.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GameResultCard(
                value = totalGamePoints,
                isPoint = true,
            )
            GameResultCard(
                value = totalGameTime,
                isPoint = false,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomButton(
                    onClick = { activity?.finish() },
                    text = R.string.Back_to_menu,
                    type = ButtonType.Primary,
                    state = ButtonState.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                CustomButton(
                    onClick = { listener.onPlayAgainClicked() },
                    text = R.string.Play_again,
                    type = ButtonType.Secondary,
                    state = ButtonState.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AflamiTheme {
        GuessGameBackground {
            ResultScreenContent(
                listener = object : ResultInteractionListener {
                    override fun onPlayAgainClicked() {}
                },
                totalGameTime = 100,
                totalGamePoints = 150,
                gameType = QuestionType.ACTOR
            )
        }
    }
}
