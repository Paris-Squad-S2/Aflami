package com.feature.mediaDetails.mediaDetailsUi.navigation.movieNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.feature.mediaDetails.mediaDetailsUi.common.components.LoginDialog
import com.feature.mediaDetails.mediaDetailsUi.navigation.Destination
import com.feature.mediaDetails.mediaDetailsUi.navigation.Graph
import kotlinx.serialization.Serializable

sealed interface MovieDetailsDestinations : Graph {

    @Serializable
    data object MovieDetailsGraph : Graph

    @Serializable
    data object MovieDetailsScreen : Destination

    @Serializable
    data class CastScreen(val movieId: Int) : Destination

    @Serializable
    data class LoginDialog(val title: String) : Destination

}

fun NavGraphBuilder.buildMovieDetailsGraph() {
    navigation<MovieDetailsDestinations.MovieDetailsGraph>(startDestination = MovieDetailsDestinations.MovieDetailsScreen) {
        composable<MovieDetailsDestinations.MovieDetailsScreen> { MovieDetailsScreen() }
        composable<MovieDetailsDestinations.CastScreen> { CastScreen() }
        dialog<MovieDetailsDestinations.LoginDialog> { LoginDialog() }
    }
}
