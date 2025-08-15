package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
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
fun GuessByImageScreen(
    modifier: Modifier = Modifier,
    viewModel: GuessByImageViewModel = hiltViewModel(),
) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    GussByImageScreenContent(
        state = screenState.value, action = viewModel, modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun GussByImageScreenContent(
    state: GuessCharacterUIState,
    action: GuessByImageInteractionListener,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 12.dp)
    ) {

        Header(
            head = state.screenTitle,
            onCanceled = action::onCancelClick,
            onTimeFinished = action::onTimeFinished,
            time = state.time
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
        )

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val currentQ = state.questionUiState.getOrNull(state.currentQuestion)
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
        val hasAnswered = state.questionUiState
            .getOrNull(state.currentQuestion)
            ?.selectedAnswer != null

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
) {
    var state by remember { mutableStateOf(GuessCardImageState.Hard) }
    GuessCard(
        imagePainter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${imageUrl}"),
        clickable = true,
        imageState = state,
        showHint = showHint && state == GuessCardImageState.Hard,
        onClick = {
            state = when (state) {
                GuessCardImageState.Hard -> {
                    onHintUsed()
                    GuessCardImageState.Medium
                }

                GuessCardImageState.Medium -> GuessCardImageState.Show
                GuessCardImageState.Show -> GuessCardImageState.Hard
            }
        },
    )
}


@Composable
private fun Header(
    head: String,
    onCanceled: () -> Unit,
    onTimeFinished: () -> Unit,
    time: Int,
) {
    AppTopBar(
        modifier = Modifier, title = head, leadingIcons = listOf(
            iconItemWithDefaults(
                ImageVector.vectorResource(R.drawable.ic_cancel), onCanceled
            )
        ), trailingContent = {
            GameTimer(
                totalSeconds = time,
                onFinished = { onTimeFinished() }
            )
        }
    )
}


@PreviewLightDark
@Composable
private fun GuessByImagePrev() {
    BasePreview {
        GussByImageScreenContent(
            state = GuessCharacterUIState(
                screenTitle = "Guess the Character",
                questionUiState = listOf(
                    QuestionUiState(
                        answers = listOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
                    )
                ),
                currentQuestion = 0
            ),
            action = object : GuessByImageInteractionListener {
                override fun onAnswerSelected(answer: String) {}
                override fun onHintUsed() {}
                override fun onNextClicked() {}
                override fun onTimeFinished() {}
                override fun onDismissNotEnoughPointsDialog() {}
                override fun onCancelClick() {}
            }
        )
    }
}