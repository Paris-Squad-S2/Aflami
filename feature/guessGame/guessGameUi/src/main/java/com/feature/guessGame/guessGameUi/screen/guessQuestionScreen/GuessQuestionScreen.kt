package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.content.res.Configuration
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.common.components.GameTimer
import com.feature.guessGame.guessGameUi.common.components.GuessCard
import com.feature.guessGame.guessGameUi.common.components.GuessGameBackground
import com.feature.guessGame.guessGameUi.common.components.NotEnoughPointsDialog
import com.feature.guessGame.guessGameUi.common.components.OptionItem
import com.feature.guessGame.guessGameUi.common.components.QuestionIndicator
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.paris.aflami.designsystem.components.AppScaffold
import com.paris.aflami.designsystem.components.AppTopBar
import com.paris.aflami.designsystem.components.ButtonState
import com.paris.aflami.designsystem.components.ButtonType
import com.paris.aflami.designsystem.components.CustomButton
import com.paris.aflami.designsystem.components.NetworkError
import com.paris.aflami.designsystem.components.PageLoadingPlaceHolder
import com.paris.aflami.designsystem.components.iconItemWithDefaults

@Composable
fun GuessQuestionScreen(
    viewModel: GuessQuestionViewModel = hiltViewModel(),
    questionType: QuestionType = QuestionType.RELEASE_YEAR,
) {
    val uiState = viewModel.screenState.collectAsStateWithLifecycle().value

    GuessGameBackground {
        GuessQuestionContent(
            state = uiState,
            listener = viewModel,
            questionType = questionType
        )
    }
}

@Composable
fun GuessQuestionContent(
    state: GuessQuestionUiState,
    listener: GuessQuestionInteractionListener,
    questionType: QuestionType,
) {
    val currentQuestionIndex = state.currentStep
    val activity = LocalActivity.current

    if (state.showNotEnoughPointsDialog) {
        NotEnoughPointsDialog(
            onDismiss = listener::onDismissNotEnoughPointsDialog,
            onConfirm = listener::onDismissNotEnoughPointsDialog
        )
    }

    when {
        state.isLoading -> {
            PageLoadingPlaceHolder(
                modifier = Modifier.fillMaxSize()
            )
        }

        state.error != null -> {
            NetworkError(
                modifier = Modifier.fillMaxSize(),
                onRetry = listener::onRetry
            )
        }

        else -> {
            AppScaffold(
                bottomBar = {
                    CustomButton(
                        onClick = listener::onNextClicked,
                        text = R.string.next,
                        type = ButtonType.Primary,
                        state = if (state.selectedAnswer != null) ButtonState.Normal else ButtonState.Disabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    AppTopBar(
                        title = stringResource(id = questionType.getTitleResId()),
                        leadingIcons = listOf(
                            iconItemWithDefaults(
                                icon = ImageVector.vectorResource(com.paris.aflami.designsystem.R.drawable.ic_cancel_thin),
                                onClick = { activity?.finish() }
                            )
                        ),
                        trailingContent = {
                            key(currentQuestionIndex) {
                                GameTimer(
                                    totalSeconds = state.timePerQuestion,
                                    onFinished = listener::onTimeFinished
                                )
                            }
                        }
                    )

                    QuestionIndicator(
                        numberOfQuestions = state.totalQuestions,
                        step = state.currentStep
                    )

                    Spacer(Modifier.height(16.dp))

                    GuessCard(
                        textNoImage = state.questionText,
                        clickable = true,
                        showHint = !state.hintUsed,
                        onClick = listener::onHintUsed,
                        onImageLoadError = listener::onRetry
                    )

                    Spacer(Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val currentOptions = state.remainingAnswers

                        currentOptions.forEach { answer ->
                            val displayText =
                                answer.genreText?.let { stringResource(id = it) } ?: answer.text

                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                OptionItem(
                                    text = displayText,
                                    selected = state.selectedAnswer == answer.text,
                                    isCorrect = state.selectedAnswer != null && answer.text == state.correctAnswer,
                                    onClick = {
                                        if (state.selectedAnswer == null) {
                                            listener.onAnswerSelected(answer.text)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    locale = "ar",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun GuessReleaseYearContentPreview() {
    GuessGameBackground {
        GuessQuestionContent(
            state = GuessQuestionUiState(
                gameTitle = "guess_release_year",
                totalQuestions = 5,
                currentStep = 2,
                questionText = "In which year was 'Inception' released?",
                answers = listOf(),
                correctAnswer = "2010",
                remainingAnswers = listOf(),
                selectedAnswer = "2008",
                hintUsed = false,
                timePerQuestion = 30,
                pointsPerQuestion = 100
            ),
            listener = object : GuessQuestionInteractionListener {
                override fun onTimeFinished() {}
                override fun onDismissNotEnoughPointsDialog() {}
                override fun onCancelClick() {}
                override fun onRetry() {}
                override fun onHintUsed() {}
                override fun onAnswerSelected(answer: String) {}
                override fun onNextClicked() {}
            },
            questionType = QuestionType.RELEASE_YEAR
        )
    }
}
