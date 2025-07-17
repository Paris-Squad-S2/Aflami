package com.paris_2.aflami.di

import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast.MovieCastViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast.TvShowCastViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsViewModelViewModel
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsViewModelViewModel
import com.feature.search.searchUi.screen.findByActor.FindByActorViewModel
import com.feature.search.searchUi.screen.search.SearchViewModel
import com.feature.search.searchUi.screen.worldTour.WorldTourViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module{
    viewModelOf(::SearchViewModel)
    viewModelOf(::FindByActorViewModel)
    viewModelOf(::WorldTourViewModel)
    viewModelOf(::MovieDetailsViewModelViewModel)
    viewModelOf(::TvShowDetailsViewModelViewModel)
    viewModelOf(::TvShowCastViewModel)
    viewModelOf(::MovieCastViewModel)
}