package com.feature.mediaDetails.mediaDetailsUi.di

import com.feature.mediaDetails.mediaDetailsUi.navigation.Destinations
import com.feature.mediaDetails.mediaDetailsUi.navigation.Navigator
import com.feature.mediaDetails.mediaDetailsUi.navigation.NavigatorImpl
import com.feature.mediaDetails.mediaDetailsUi.screen.movie.MovieViewModel
import com.feature.mediaDetails.mediaDetailsUi.screen.tvShow.TvShowViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
   /* val viewModelModule = module{
        viewModelOf(::MovieViewModel)
        viewModelOf(::TvShowViewModel)
    }*/
   single<Navigator> { NavigatorImpl(startGraph = Destinations.MediaDetailsGraph) }
}
