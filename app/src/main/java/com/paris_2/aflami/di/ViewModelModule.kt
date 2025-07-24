package com.paris_2.aflami.di

import com.feature.home.homeUi.screen.continueWatching.ContinueWatchingViewModel
import com.feature.home.homeUi.screen.home.HomeScreenViewModel
import com.feature.home.homeUi.screen.topRatingMovies.TopRatingMoviesViewModel
import com.feature.authentication.authenticationUi.screen.forgotPassword.ForgotPasswordViewModel
import com.feature.authentication.authenticationUi.screen.login.LoginViewModel
import com.feature.authentication.authenticationUi.screen.register.RegisterViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast.MovieCastViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast.TvShowCastViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsViewModelViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.video.VideoWebViewViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsViewModel
import com.feature.search.searchUi.screen.findByActor.FindByActorViewModel
import com.feature.search.searchUi.screen.search.SearchViewModel
import com.feature.search.searchUi.screen.worldTour.WorldTourViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::FindByActorViewModel)
    viewModelOf(::WorldTourViewModel)
    viewModelOf(::MovieDetailsViewModelViewModel)
    viewModelOf(::TvShowDetailsViewModel)
    viewModelOf(::TvShowCastViewModel)
    viewModelOf(::MovieCastViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::VideoWebViewViewModel)
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::ContinueWatchingViewModel)
    viewModelOf(::TopRatingMoviesViewModel)
}