package com.feature.guessGame.guessGameUi.screen.guessbyimage

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
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
    activity: GuessByImageActivity,
    viewModel: GuessByImageViewModel = hiltViewModel(),
) {

    val intent = activity.intent
    //  val questionType = intent.getStringExtra("question_type") ?: "Guess Poster"
    val gameLevelName = intent.getStringExtra("game_level") ?: UiGameLevel.EASY.name
    val imageTypeName = intent.getStringExtra("image_type") ?: QuestionType.ACTOR.name

    val gameLevel = runCatching { UiGameLevel.valueOf(gameLevelName) }
        .getOrDefault(UiGameLevel.EASY)
    val imageType = runCatching { QuestionType.valueOf(imageTypeName) }
        .getOrDefault(QuestionType.ACTOR)

//    val totalQuestions = intent.getIntExtra("total_questions", 10)
//    val timePerQuestion = intent.getIntExtra("time_per_question", 30)
//    val pointsPerQuestion = intent.getIntExtra("points_per_question", 10)

    val state = viewModel.screenState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = Unit) {
        viewModel.initialization(gameLevel, imageType)
    }

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
    if (state.isLoading) {
        PageLoadingPlaceHolder(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        if (state.showNotEnoughPointsDialog) {
            NotEnoughPointsDialog(
                onDismiss = listener::onDismissNotEnoughPointsDialog,
                onConfirm = listener::onDismissNotEnoughPointsDialog,
                title = com.feature.guessGame.guessGameUi.R.string.Not_enough_points
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Header(
                head = state.screenTitle,
                onTimeFinished = listener::onTimeFinished,
                time = state.time,
                currentQuestion = state.currentQuestion,
                context = LocalContext.current
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
                    showHint = !state.isChoiceCorrect,
                    onHintUsed = listener::onHintUsed,
                    imageUrl = currentQuestion?.image.orEmpty(),
                    uiState = currentQuestion ?: QuestionUiState()
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
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


    }
}

@Composable
fun QuestionImage(
    imageUrl: String,
    uiState: QuestionUiState,
    showHint: Boolean = true,
    onHintUsed: () -> Unit = {},
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
        }
    )
}


@Composable
private fun Header(
    head: Int?,
    context: Context,
    onTimeFinished: () -> Unit,
    time: Int,
    currentQuestion: Int,
) {
    AppTopBar(
        modifier = Modifier,
        title = stringResource(
            id = head ?: com.feature.guessGame.guessGameUi.R.string.guess_the_poster
        ),
        leadingIcons = listOf(
            iconItemWithDefaults(
                ImageVector.vectorResource(R.drawable.ic_cancel), onClick = {
                    (context as? Activity)?.finish()
                }
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