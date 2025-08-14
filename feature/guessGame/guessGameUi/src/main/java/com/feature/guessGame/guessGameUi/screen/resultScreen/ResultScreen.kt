package com.feature.guessGame.guessGameUi.screen.resultScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.common.components.GameResultCard
import com.feature.guessGame.guessGameUi.common.components.GuessQuestionBackground
import com.feature.guessGame.guessGameUi.common.components.WinCard
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults
import com.paris_2.aflami.designsystem.theme.AflamiTheme

@Composable
fun ResultScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    questionType: QuestionType = QuestionType.RELEASE_YEAR,
) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle().value

    GuessQuestionBackground {
        ResultScreenContent(
            state = uiState,
            listener = viewModel
        )
    }
}

@Composable
fun ResultScreenContent(
    state: ResultUiState,
    listener: ResultInteractionListener,
) {
    Column {
        AppTopBar(
            title = state.gameType,
            leadingIcons = listOf(
                iconItemWithDefaults(
                    icon = ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_cancel),
                    onClick = { listener.onExitClicked() }
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
                value = state.totalSessionPoints,
                isPoint = true,
            )
            GameResultCard(
                value = state.totalSessionDuration,
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
                    onClick = { listener.onBackToMenuClicked() },
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
        GuessQuestionBackground {
            ResultScreenContent(
                state = ResultUiState(
                    gameType = "Guess the character",
                    totalSessionPoints = 120,
                    totalSessionDuration = 45
                ),
                listener = object : ResultInteractionListener {
                    override fun onExitClicked() {}
                    override fun onBackToMenuClicked() {}
                    override fun onPlayAgainClicked() {}
                }
            )
        }
    }
}
