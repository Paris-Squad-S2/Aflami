package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.feature.guessGame.guessGameUi.common.components.GameTimer
import com.feature.guessGame.guessGameUi.common.components.GuessGameBackground
import com.feature.guessGame.guessGameUi.common.components.NotEnoughPointsDialog
import com.feature.guessGame.guessGameUi.common.components.OptionItem
import com.feature.guessGame.guessGameUi.common.components.QuestionIndicator
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.GuessCard
import com.paris_2.aflami.designsystem.components.GuessCardImageState
import com.paris_2.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults


@Composable
fun GuessByImageScreen(
    modifier: Modifier = Modifier,
    viewModel: GuessByImageViewModel = hiltViewModel(),
) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    GuessGameBackground {
        GussByImageScreenContent(
            state = screenState.value,
            action = viewModel,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun GussByImageScreenContent(
    state: GuessCharacterUIState,
    action: GuessByImageInteractionListener,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        PageLoadingPlaceHolder(
            modifier = Modifier
                .fillMaxSize()
        )
        return
    }

    if (state.showNotEnoughPointsDialog) {
        NotEnoughPointsDialog(
            onDismiss = action::onDismissNotEnoughPointsDialog,
            onConfirm = action::onDismissNotEnoughPointsDialog,
            title = com.feature.guessGame.guessGameUi.R.string.Not_enough_points,
        )
    }

    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 12.dp)
    ) {
        Header(
            head = state.screenTitle,
            onCanceled = action::onCancelClick,
            onTimeFinished = action::onTimeFinished,
            time = state.time,
            currentQuestion = state.currentQuestion
        )

        QuestionIndicator(
            numberOfQuestions = state.questionUiState.size,
            step = state.currentQuestion,
            modifier = Modifier.padding(vertical = 18.dp)
        )

        val currentQ = state.questionUiState.getOrNull(state.currentQuestion)
        QuestionImage(
            showHint = !state.isChoiceCorrect,
            onHintUsed = { action.onHintUsed() },
            imageUrl = currentQ?.image ?: "",
            uiState = currentQ ?: QuestionUiState()
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            currentQ?.answers?.let { answers ->
                items(answers) { answer ->
                    val isSelected = answer == currentQ.selectedAnswer
                    val isCorrect = answer == currentQ.correctAnswer

                    OptionItem(
                        text = answer,
                        selected = isSelected,
                        isCorrect = if (currentQ.selectedAnswer != null) isCorrect else false,
                        onClick = { action.onAnswerSelected(answer) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        val hasAnswered = currentQ?.selectedAnswer != null
        CustomButton(
            onClick = { action.onNextClicked() },
            text = com.feature.guessGame.guessGameUi.R.string.next,
            type = ButtonType.Primary,
            state = if (hasAnswered) ButtonState.Normal else ButtonState.Disabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
fun QuestionImage(
    modifier: Modifier = Modifier,
    showHint: Boolean = true,
    onHintUsed: () -> Unit = {},
    imageUrl: String,
    uiState: QuestionUiState,
) {
    var state by remember { mutableStateOf(GuessCardImageState.Hard) }
    GuessCard(
        imagePainter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500$imageUrl"),
        clickable = !uiState.usedHint,
        imageState = state,
        showHint = showHint && !uiState.usedHint && state == GuessCardImageState.Hard,
        onClick = {
            if (!uiState.usedHint && state == GuessCardImageState.Hard) {
                onHintUsed()
                state = GuessCardImageState.Medium
            }
        },
    )
}


@Composable
private fun Header(
    head: Int,
    onCanceled: () -> Unit,
    onTimeFinished: () -> Unit,
    time: Int,
    currentQuestion: Int,
) {
    AppTopBar(
        modifier = Modifier,
        title = stringResource(id = head),
        leadingIcons = listOf(
            iconItemWithDefaults(
                ImageVector.vectorResource(R.drawable.ic_cancel), onCanceled
            )
        ),
        trailingContent = {
            key(currentQuestion) {
                GameTimer(
                    totalSeconds = time,
                    onFinished = { onTimeFinished() }
                )
            }
        }
    )
}