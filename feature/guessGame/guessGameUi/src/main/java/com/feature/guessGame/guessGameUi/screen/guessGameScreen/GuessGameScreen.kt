package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.guessGame.guessGameUi.common.components.DifficultyDialog
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.components.PointsBadge
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.GameCard
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme
import com.feature.guessGame.guessGameUi.R as GuessR

@Composable
fun GuessGameScreen(
    viewModel: GuessGameScreenViewModel = hiltViewModel(),
) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle()
    GuessGameScreenContent(state = screenState.value, action = viewModel)
}

@Composable
fun GuessGameScreenContent(
    state: GuessGameScreenUiState,
    action: GuessGameScreenInteractionListener,
) {
    val layoutDirection =
        if (LocalConfiguration.current.layoutDirection == android.util.LayoutDirection.RTL)
            LayoutDirection.Rtl else LayoutDirection.Ltr

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .statusBarsPadding()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            AppTopBar(
                title = stringResource(R.string.let_s_play),
                trailingContent = { PointsBadge(points = state.userPoints) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .systemBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val gamesToShow = getStaticGames().map { game ->
                game.copy(
                    isLocked = state.userPoints < game.pointsToUnlock
                )
            }

            gamesToShow.forEach { game ->
                GameCard(
                    title = game.title,
                    description = game.description,
                    backgroundColors = game.backgroundColors,
                    trailingImages = game.trailingImages,
                    onPlayClick = { action.onGamePlayClicked(game.id) },
                    isPlayButtonLocked = game.isLocked,
                    pointsToUnlock = game.pointsToUnlock
                )
            }

            if (state.showDifficultyDialog) {
                DifficultyDialog(
                    selectedDifficulty = state.selectedDifficulty,
                    onDismiss = action::onDismissDifficultyDialog,
                    onClickButton = { action.onStartGame(context) },
                    onSelectChip = action::onSelectDifficulty
                )
            }
        }
    }
}

val imageIds = listOf(
    R.drawable.image_game3,
    R.drawable.image_game2,
    R.drawable.image_game1,
)

@Composable
private fun getStaticGames(): List<GameData> = listOf(
    GameData(
        id = "guess_character",
        title = stringResource(GuessR.string.guess_the_character_title),
        description = stringResource(GuessR.string.guess_the_character_desc),
        backgroundColors = listOf(Theme.colors.primaryVariant, Theme.colors.primary),
        trailingImages = listOf(painterResource(R.drawable.image_clown)),
        isLocked = false
    ),
    GameData(
        id = "guess_movie",
        title = stringResource(GuessR.string.guess_the_movie_title),
        description = stringResource(GuessR.string.guess_the_movie_desc),
        backgroundColors = listOf(Theme.colors.status.blueCard, Theme.colors.status.blueAccent),
        trailingImages = imageIds.map { painterResource(it) },
        isLocked = false
    ),
    GameData(
        id = "guess_release_year",
        title = stringResource(GuessR.string.when_was_it_released_title),
        description = stringResource(GuessR.string.when_was_it_released_desc),
        backgroundColors = listOf(Theme.colors.status.navyCard, Theme.colors.status.darkBlue),
        trailingImages = listOf(painterResource(R.drawable.ic_purpl_calendar)),
        isLocked = true,
        pointsToUnlock = 5
    ),
    GameData(
        id = "guess_genre",
        title = stringResource(GuessR.string.which_genre_title),
        description = stringResource(GuessR.string.which_genre_desc),
        backgroundColors = listOf(Theme.colors.status.yellowCard, Theme.colors.status.yellowAccent),
        trailingImages = listOf(painterResource(R.drawable.image_chair)),
        isLocked = true,
        pointsToUnlock = 5
    )
)

@Preview(showBackground = true, showSystemUi = true, name = "LTR")
@Preview(showBackground = true, name = "RTL", locale = "ar")
@Composable
fun GuessGameScreenPreview() {
    AflamiTheme {
        GuessGameScreenContent(
            state = GuessGameScreenUiState(userPoints = 120),
            action = object : GuessGameScreenInteractionListener {
                override fun onGamePlayClicked(gameId: String) {}
                override fun onSelectDifficulty(difficultyId: Int) {}
                override fun onStartGame(context: Context) {}
                override fun onDismissDifficultyDialog() {}
            }
        )
    }
}
