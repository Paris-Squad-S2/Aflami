package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.guessGame.guessGameUi.common.components.GameTimer
import com.feature.guessGame.guessGameUi.common.components.GuessCard
import com.feature.guessGame.guessGameUi.common.components.GuessCardImageState
import com.feature.guessGame.guessGameUi.common.components.GuessGameBackground
import com.feature.guessGame.guessGameUi.common.components.NotEnoughPointsDialog
import com.feature.guessGame.guessGameUi.common.components.OptionItem
import com.feature.guessGame.guessGameUi.common.components.QuestionIndicator
import com.paris.aflami.designsystem.R
import com.paris.aflami.designsystem.components.AppTopBar
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.components.NetworkError
import com.paris.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris.aflami.designsystem.components.iconItemWithDefaults


@Composable
fun GuessByImageScreen(
    viewModel: GuessByImageViewModel = hiltViewModel(),
) {
    val state = viewModel.screenState.collectAsStateWithLifecycle().value

    GuessGameBackground {
        GuessByImageContent(
            state = state,
            listener = viewModel
        )
    }
}

@Composable
fun GuessByImageContent(
    state: GuessCharacterUIState,
    listener: GuessByImageInteractionListener,
) {
    val activity = LocalActivity.current

    if (state.showNotEnoughPointsDialog) {
        NotEnoughPointsDialog(
            onDismiss = listener::onDismissNotEnoughPointsDialog,
            onConfirm = listener::onDismissNotEnoughPointsDialog,
            title = com.feature.guessGame.guessGameUi.R.string.Not_enough_points
        )
    }

    when {
        state.isLoading -> {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        }

        state.error!= null -> {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = listener::onRetry
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Header(
                    head = state.screenTitle,
                    onCanceled = { activity?.finish() },
                    onTimeFinished = listener::onTimeFinished,
                    time = state.time,
                    currentQuestion = state.currentQuestion
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    QuestionIndicator(
                        numberOfQuestions = state.questionUiState.size,
                        step = state.currentQuestion,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    val currentQuestion = state.questionUiState.getOrNull(state.currentQuestion)

                    QuestionImage(
                        onHintUsed = { listener.onHintUsed() },
                        imageUrl = currentQuestion?.image.orEmpty(),
                        uiState = currentQuestion ?: QuestionUiState(),
                        onReloadQuestion = listener::onRetry
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        currentQuestion?.answers?.forEach { answer ->
                            val isSelected = answer == currentQuestion.selectedAnswer
                            val isCorrect = answer == currentQuestion.correctAnswer

                            OptionItem(
                                text = answer,
                                selected = isSelected,
                                isCorrect = currentQuestion.selectedAnswer != null && isCorrect,
                                onClick = { listener.onAnswerSelected(answer) }
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    CustomButton(
                        onClick = listener::onNextClicked,
                        text = com.feature.guessGame.guessGameUi.R.string.next,
                        type = ButtonType.Primary,
                        state = if (currentQuestion?.selectedAnswer != null) ButtonState.Normal else ButtonState.Disabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionImage(
    imageUrl: String,
    uiState: QuestionUiState,
    onHintUsed: () -> Unit = {},
    onReloadQuestion: () -> Unit = {}
) {
    val imageState = if (uiState.usedHint) {
        GuessCardImageState.Medium
    } else {
        GuessCardImageState.Hard
    }
   Log.d("QuestionImage", "QuestionImage: $imageUrl")
    GuessCard(
        imageUrl = GuessByImageViewModel.IMAGE_BASE_URL+imageUrl,
        clickable = !uiState.usedHint,
        imageState = imageState,
        onImageLoadError = onReloadQuestion,
        showHint = !uiState.usedHint,
        onClick = {
            if (!uiState.usedHint) {
                onHintUsed()
            }
        }
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
                ImageVector.vectorResource(R.drawable.ic_cancel_thin), onCanceled
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