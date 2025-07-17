package com.paris_2.aflami.di

import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.MediaDetailsFeatureAPIImpl
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigatorImpl
import com.feature.search.searchApi.SearchDestinations
import com.feature.search.searchApi.SearchFeatureAPI
import com.feature.search.searchUi.SearchFeatureAPIImpl
import com.feature.search.searchUi.navigation.SearchNavigator
import com.feature.search.searchUi.navigation.SearchNavigatorImpl
import com.paris_2.aflami.appnavigation.AppDestinations
import com.paris_2.aflami.appnavigation.AppNavigator
import com.paris_2.aflami.appnavigation.AppNavigatorImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val FeatureAPIModule = module {
    single<AppNavigator> { AppNavigatorImpl(startGraph = AppDestinations.AppGraph1) }
    single<SearchNavigator> { SearchNavigatorImpl(startGraph = SearchDestinations.SearchGraph1) }
    single<MediaDetailsNavigator> { MediaDetailsNavigatorImpl(startGraph = MediaDetailsDestinations.MediaDetailsGraph1) }

    factoryOf(::SearchFeatureAPIImpl) bind SearchFeatureAPI::class
    factoryOf(::MediaDetailsFeatureAPIImpl) bind MediaDetailsFeatureAPI ::class
}