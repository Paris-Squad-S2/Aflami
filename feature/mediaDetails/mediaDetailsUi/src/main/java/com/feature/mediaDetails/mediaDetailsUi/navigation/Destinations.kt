package com.feature.mediaDetails.mediaDetailsUi.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.feature.mediaDetails.mediaDetailsUi.common.components.LoginDialog
import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {

    @Serializable
    data object MediaDetailsGraph : Graph

    @Serializable
    data object MovieDetailsScreen : Destination

    @Serializable
    data object TvShowDetailsScreen : Destination

    @Serializable
    data class CastScreen(val movieId: Int) : Destination

    @Serializable
    data class LoginDialog(val title:String):Destination

}

fun NavGraphBuilder.buildMediaDetailsGraph() {
    navigation<Destinations.MediaDetailsGraph>(startDestination = Destinations.MovieDetailsScreen) {
        composable<Destinations.MovieDetailsScreen> { MovieDetailsScreen() }
        composable<Destinations.TvShowDetailsScreen> { TvShowDetailsScreen() }
        composable<Destinations.CastScreen> { CastScreen() }
        dialog<Destinations.LoginDialog>{ LoginDialog() }
    }
}
