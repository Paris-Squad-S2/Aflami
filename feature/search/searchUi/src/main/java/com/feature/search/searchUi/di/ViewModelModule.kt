package com.feature.search.searchUi.di

import com.feature.search.searchUi.navigation.Destinations
import com.feature.search.searchUi.navigation.Navigator
import com.feature.search.searchUi.navigation.NavigatorImpl
import com.feature.search.searchUi.screen.findByActor.FindByActorViewModel
import com.feature.search.searchUi.screen.search.SearchViewModel
import com.feature.search.searchUi.screen.worldTour.WorldTourViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module{
    viewModelOf(::SearchViewModel)
    viewModelOf(::FindByActorViewModel)
    viewModelOf(::WorldTourViewModel)
    single<Navigator> { NavigatorImpl(startGraph = Destinations.SearchGraph) }
}