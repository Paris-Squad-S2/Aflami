package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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
import com.feature.guessGame.guessGameUi.common.components.GuessQuestionBackground
import com.feature.guessGame.guessGameUi.common.components.OptionItem
import com.feature.guessGame.guessGameUi.common.components.QuestionIndicator
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.ButtonState
import com.paris_2.aflami.designsystem.components.ButtonType
import com.paris_2.aflami.designsystem.components.CustomButton
import com.paris_2.aflami.designsystem.components.GuessCard
import com.paris_2.aflami.designsystem.components.iconItemWithDefaults

@Composable
fun GuessQuestionScreen(
    viewModel: GuessQuestionViewModel = hiltViewModel(),
    questionType: QuestionType = QuestionType.RELEASE_YEAR,
) {
    val guessQuestionScreenState = viewModel.screenState.collectAsStateWithLifecycle()

    val background: @Composable (@Composable () -> Unit) -> Unit =
        when (questionType) {
            QuestionType.RELEASE_YEAR -> { content -> GuessQuestionBackground { content() } }
            QuestionType.GENRE -> { content -> GuessQuestionBackground { content() } }
        }
    background {
        GuessQuestionContent(
            state = guessQuestionScreenState.value,
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()

    ) {
        AppTopBar(
            title = stringResource(id = getGameTitleResId(questionType)),
            leadingIcons = listOf(
                iconItemWithDefaults(
                    ImageVector.vectorResource(com.paris_2.aflami.designsystem.R.drawable.ic_cancel),
                    {}
                )
            ),
            trailingContent = {
                GameTimer(
                    totalSeconds = state.timePerQuestion,
                    onFinished = { listener.onTimeFinished() }
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            QuestionIndicator(
                numberOfQuestions = state.totalQuestions,
                step = state.currentStep
            )

            Spacer(Modifier.height(16.dp))

            GuessCard(
                textNoImage = state.questionText,
                clickable = false,
                showHint = !state.hintUsed,
                hintPoints = 10,
                onClick = { listener.onHintUsed() }
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.remainingAnswers) { answer ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        OptionItem(
                            text = answer,
                            selected = state.selectedAnswer == answer,
                            isCorrect = state.correctAnswer == answer,
                            onClick = { listener.onAnswerSelected(answer) }
                        )
                    }
                }
            }


            Spacer(
                Modifier
                    .weight(1f)
                    .height(24.dp)
            )

            CustomButton(
                onClick = { listener.onNextClicked() },
                text = R.string.next,
                type = ButtonType.Primary,
                state = if (state.selectedAnswer != null) ButtonState.Normal else ButtonState.Disabled,
                modifier = Modifier.fillMaxWidth()
            )
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
    val previewState = GuessQuestionUiState(
        gameTitle = "guess_release_year",
        totalQuestions = 5,
        currentStep = 2,
        questionText = "In which year was 'Inception' released?",
        answers = listOf("2008", "2010", "2012", "2014"),
        correctAnswer = "2010",
        remainingAnswers = listOf("2008", "2010", "2012", "2014"),
        selectedAnswer = "2008",
        hintUsed = false,
        timePerQuestion = 30,
        pointsPerQuestion = 100
    )

    val previewListener = object : GuessQuestionInteractionListener {
        override fun onTimeFinished() {}
        override fun onHintUsed() {}
        override fun onAnswerSelected(answer: String) {}
        override fun onNextClicked() {}
    }
    GuessQuestionBackground {
        GuessQuestionContent(
            state = previewState,
            listener = previewListener,
            questionType = QuestionType.RELEASE_YEAR
        )
    }
}
