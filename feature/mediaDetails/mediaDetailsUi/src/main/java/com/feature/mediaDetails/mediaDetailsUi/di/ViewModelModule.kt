/*
package com.feature.mediaDetails.mediaDetailsUi.di

import com.feature.mediaDetails.mediaDetailsUi.navigation.movieNavigation.MovieDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.navigation.Navigator
import com.feature.mediaDetails.mediaDetailsUi.navigation.NavigatorImpl
import com.feature.mediaDetails.mediaDetailsUi.navigation.tvShowNavigation.TvShowDetailsDestinations
import org.koin.dsl.module

val viewModelModule = module {

val viewModelModule = module{
        viewModelOf(::MovieViewModel)
        viewModelOf(::TvShowViewModel)
    }

   single<Navigator> { NavigatorImpl(startGraph = MovieDetailsDestinations.MovieDetailsGraph) }
   single<Navigator> { NavigatorImpl(startGraph = TvShowDetailsDestinations.TvShowDetailsGraph) }

}
*/
