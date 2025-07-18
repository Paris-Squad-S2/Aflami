package com.paris_2.aflami.di

import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.categories.categoriesUi.CategoriesFeatureAPIImpl
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.guessGame.guessGameUi.GuessGameFeatureAPIImpl
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.home.homeUi.HomeFeatureAPIImpl
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.lists.listsUi.ListsFeatureAPIImpl
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.MediaDetailsFeatureAPIImpl
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigatorImpl
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.feature.profile.profileUi.ProfileFeatureAPIImpl
import com.feature.search.searchApi.SearchDestinations
import com.feature.search.searchApi.SearchFeatureAPI
import com.feature.search.searchUi.SearchFeatureAPIImpl
import com.feature.search.searchUi.navigation.SearchNavigator
import com.feature.search.searchUi.navigation.SearchNavigatorImpl
import com.paris_2.aflami.AppNavigatorImpl
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
    factory<MediaDetailsFeatureAPI> { MediaDetailsFeatureAPIImpl() }


    factory<HomeFeatureAPI> { HomeFeatureAPIImpl() }
    factory<ListsFeatureAPI> { ListsFeatureAPIImpl() }
    factory<CategoriesFeatureAPI> { CategoriesFeatureAPIImpl() }
    factory<GuessGameFeatureAPI> { GuessGameFeatureAPIImpl() }
    factory<ProfileFeatureAPI> { ProfileFeatureAPIImpl() }

    factoryOf(::SearchFeatureAPIImpl) bind SearchFeatureAPI::class
    factoryOf(::MediaDetailsFeatureAPIImpl) bind MediaDetailsFeatureAPI ::class
}
