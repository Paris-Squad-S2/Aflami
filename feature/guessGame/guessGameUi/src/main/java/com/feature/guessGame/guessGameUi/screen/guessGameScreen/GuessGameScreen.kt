package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.components.PointsBadge
import com.paris_2.aflami.designsystem.R
import com.paris_2.aflami.designsystem.components.AppTopBar
import com.paris_2.aflami.designsystem.components.GameCard
import com.paris_2.aflami.designsystem.theme.AflamiTheme
import com.paris_2.aflami.designsystem.theme.Theme

@Composable
fun GuessGameScreen(
    viewModel: GuessGameScreenViewModel = hiltViewModel(),
) {
    val letsPlayScreenState = viewModel.screenState.collectAsStateWithLifecycle()
    GuessGameScreenContent(state = letsPlayScreenState.value, action = viewModel)
}

@Composable
fun GuessGameScreenContent(
    state: GuessGameScreenUiState,
    action: GuessGameScreenInteractionListener,
) {
    val games = listOf(
        GameData(
            id = "guess_character",
            title = stringResource(com.feature.guessGame.guessGameUi.R.string.guess_the_character_title),
            description = stringResource(com.feature.guessGame.guessGameUi.R.string.guess_the_character_desc),
            backgroundColors = listOf(Theme.colors.primaryVariant, Theme.colors.primary),
            trailingImages = listOf(painterResource(R.drawable.image_clown)),
            isLocked = false
        ),
        GameData(
            id = "guess_movie",
            title = stringResource(com.feature.guessGame.guessGameUi.R.string.guess_the_movie_title),
            description = stringResource(com.feature.guessGame.guessGameUi.R.string.guess_the_movie_desc),
            backgroundColors = listOf(Theme.colors.status.blueCard, Theme.colors.status.blueAccent),
            trailingImages = listOf(
                painterResource(R.drawable.image_game2),
                painterResource(R.drawable.image_game2),
                painterResource(R.drawable.image_game2)
            ),
            isLocked = false
        ),
        GameData(
            id = "guess_release_year",
            title = stringResource(com.feature.guessGame.guessGameUi.R.string.when_was_it_released_title),
            description = stringResource(com.feature.guessGame.guessGameUi.R.string.when_was_it_released_desc),
            backgroundColors = listOf(Theme.colors.status.navyCard, Theme.colors.status.darkBlue),
            trailingImages = listOf(painterResource(R.drawable.ic_purpl_calendar)),
            isLocked = true,
            pointsToUnlock = 400
        ),
        GameData(
            id = "guess_genre",
            title = stringResource(com.feature.guessGame.guessGameUi.R.string.which_genre_title),
            description = stringResource(com.feature.guessGame.guessGameUi.R.string.which_genre_desc),
            backgroundColors = listOf(
                Theme.colors.status.yellowCard,
                Theme.colors.status.yellowAccent
            ),
            trailingImages = listOf(painterResource(R.drawable.image_chair)),
            isLocked = true,
            pointsToUnlock = 400
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .statusBarsPadding()
    ) {
        val layoutDirection =
            if (LocalConfiguration.current.layoutDirection == android.util.LayoutDirection.RTL)
                LayoutDirection.Rtl else LayoutDirection.Ltr
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            AppTopBar(
                title = stringResource(R.string.let_s_play),
                trailingContent = {
                    PointsBadge(points = state.userPoints)
                }
            )
        }

        val gamesToShow = games.mapIndexed { index, staticGame ->
            staticGame.copy(
                isLocked = state.games.getOrNull(index)?.isLocked ?: staticGame.isLocked,
                pointsToUnlock = state.games.getOrNull(index)?.pointsToUnlock
                    ?: staticGame.pointsToUnlock
            )
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(gamesToShow) { game ->
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
        }
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Preview(showBackground = true, name = "LTR")
@Preview(showBackground = true, name = "RTL", locale = "ar")
fun LetsPlayScreenPreview() {
    AflamiTheme {
        GuessGameScreenContent(
            state = GuessGameScreenUiState(userPoints = 120),
            action = object : GuessGameScreenInteractionListener {
                override fun onGamePlayClicked(gameTitle: String) {}
            }
        )
    }
}


