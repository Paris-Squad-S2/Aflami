package com.paris_2.aflami.di

import com.feature.search.searchUi.navigation.SearchDestinations
import com.paris_2.aflami.appnavigation.Navigator
import com.paris_2.aflami.appnavigation.NavigatorImpl
import com.feature.search.searchUi.screen.findByActor.FindByActorViewModel
import com.feature.search.searchUi.screen.search.SearchViewModel
import com.feature.search.searchUi.screen.worldTour.WorldTourViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module{
    viewModelOf(::SearchViewModel)
    viewModelOf(::FindByActorViewModel)
    viewModelOf(::WorldTourViewModel)
    single<Navigator> { NavigatorImpl(startGraph = SearchDestinations.SearchGraph) }
}