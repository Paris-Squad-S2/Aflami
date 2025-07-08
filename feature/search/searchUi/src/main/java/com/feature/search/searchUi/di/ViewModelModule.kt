package com.feature.search.searchUi.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.feature.search.searchUi.screen.search.SearchViewModel
import com.feature.search.searchUi.screen.filter.FilterViewModel
import com.feature.search.searchUi.screen.findByActor.FindByActorViewModel
import com.feature.search.searchUi.screen.worldTour.WorldTourViewModel

val viewModelModule = module{
    viewModelOf(::SearchViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::FindByActorViewModel)
    viewModelOf(::WorldTourViewModel)
}