package com.feature.mediaDetails.mediaDetailsUi.navigation.tvShowNavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.feature.mediaDetails.mediaDetailsUi.navigation.Destination
import com.feature.mediaDetails.mediaDetailsUi.navigation.Graph
import kotlinx.serialization.Serializable

sealed interface TvShowDetailsDestinations {
    @Serializable
    data object TvShowDetailsGraph : Graph

    @Serializable
    data object TvShowDetailsScreen : Destination

    @Serializable
    data class CastScreen(val movieId: Int) : Destination

    @Serializable
    data class LoginDialog(val title: String) : Destination

}

fun NavGraphBuilder.buildTvShowDetailsGraph() {
    navigation<TvShowDetailsDestinations.TvShowDetailsGraph>(startDestination = TvShowDetailsDestinations.TvShowDetailsScreen) {
//        composable<TvShowDetailsDestinations.TvShowDetailsScreen> { TvShowDetailsScreen() }
//        composable<TvShowDetailsDestinations.CastScreen> { CastScreen() }
//        dialog<TvShowDetailsDestinations.LoginDialog> { LoginDialog() }
    }
}
