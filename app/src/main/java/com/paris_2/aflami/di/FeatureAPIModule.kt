package com.paris_2.aflami.di

import android.content.Context
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.authentication.authenticationUi.AuthenticationFeatureAPIImpl
import com.feature.authentication.authenticationUi.navigation.AuthenticationDestinations
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigatorImpl
import com.feature.categories.categoriesApi.CategoriesFeatureAPI
import com.feature.categories.categoriesUi.CategoriesFeatureAPIImpl
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.guessGame.guessGameUi.GuessGameFeatureAPIImpl
import com.feature.guessGame.guessGameUi.navigation.GuessGameDestinations
import com.feature.guessGame.guessGameUi.navigation.GuessGameNavigator
import com.feature.guessGame.guessGameUi.navigation.GuessGameNavigatorImpl
import com.feature.home.homeApi.HomeFeatureAPI
import com.feature.home.homeUi.HomeFeatureAPIImpl
import com.feature.lists.listsApi.ListsFeatureAPI
import com.feature.lists.listsUi.ListsFeatureAPIImpl
import com.feature.lists.listsUi.navigation.ListDestinations
import com.feature.lists.listsUi.navigation.ListNavigator
import com.feature.lists.listsUi.navigation.ListNavigatorImpl
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.MediaDetailsFeatureAPIImpl
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigator
import com.feature.mediaDetails.mediaDetailsUi.ui.navigation.MediaDetailsNavigatorImpl
import com.feature.onboarding.onboardingApi.OnBoardingFeatureAPI
import com.feature.onboarding.onboardingUi.OnBoardingFeatureAPIImpl
import com.feature.profile.profileApi.ProfileFeatureAPI
import com.feature.profile.profileUi.ProfileFeatureAPIImpl
import com.feature.profile.profileUi.navigation.ProfileDestinations
import com.feature.profile.profileUi.navigation.ProfileNavigator
import com.feature.profile.profileUi.navigation.ProfileNavigatorImpl
import com.feature.search.searchApi.SearchFeatureAPI
import com.feature.search.searchUi.SearchFeatureAPIImpl
import com.feature.search.searchUi.navigation.SearchDestinations
import com.feature.search.searchUi.navigation.SearchNavigator
import com.feature.search.searchUi.navigation.SearchNavigatorImpl
import com.paris_2.aflami.bottomNavBar.bottomNavBarAPI.BottomNavBarAPI
import com.paris_2.aflami.bottomNavBar.bottomNavBarUI.BottomNavBarAPIImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureAPIModule {
    @Provides
    @Singleton
    fun provideAppNavigator(): com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Navigator =
        com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.NavigatorImpl(com.paris_2.aflami.bottomNavBar.bottomNavBarUI.navigation.Destinations.MainGraph)

    @Provides
    @Singleton
    fun provideOnBoardingFeatureAPI(@ApplicationContext context: Context): OnBoardingFeatureAPI =
        OnBoardingFeatureAPIImpl(context)

    @Provides
    @Singleton
    fun provideSearchNavigator(): SearchNavigator =
        SearchNavigatorImpl(startGraph = SearchDestinations.SearchGraph1)

    @Provides
    @Singleton
    fun provideProfileNavigator(): ProfileNavigator =
        ProfileNavigatorImpl(startGraph = ProfileDestinations.ProfileMainGraph)

    @Provides
    @Singleton
    fun provideMediaDetailsNavigator(): MediaDetailsNavigator =
        MediaDetailsNavigatorImpl(startGraph = MediaDetailsDestinations.MediaDetailsGraph1)

    @Provides
    @Singleton
    fun provideAuthenticationNavigator(): AuthenticationNavigator =
        AuthenticationNavigatorImpl(startGraph = AuthenticationDestinations.AuthenticationGraph1)


    @Provides
    @Singleton
    fun provideListNavigator(): ListNavigator =
        ListNavigatorImpl(startGraph = ListDestinations.ListGraph1)

    @Provides
    @Singleton
    fun provideGuessGameNavigator(): GuessGameNavigator =
        GuessGameNavigatorImpl(startGraph = GuessGameDestinations.GuessGameGraph1)


    @Provides
    fun provideHomeFeatureAPI(): HomeFeatureAPI = HomeFeatureAPIImpl()

    @Provides
    fun provideListsFeatureAPI(): ListsFeatureAPI = ListsFeatureAPIImpl()

    @Provides
    fun provideCategoriesFeatureAPI(): CategoriesFeatureAPI = CategoriesFeatureAPIImpl()

    @Provides
    fun provideGuessGameFeatureAPI(): GuessGameFeatureAPI = GuessGameFeatureAPIImpl()

    @Provides
    fun provideProfileFeatureAPI(): ProfileFeatureAPI = ProfileFeatureAPIImpl()

    @Provides
    @Singleton
    fun provideMediaDetailsFeatureAPI(@ApplicationContext context: Context): MediaDetailsFeatureAPI =
        MediaDetailsFeatureAPIImpl(context)

    @Provides
    @Singleton
    fun provideSearchFeatureAPI(@ApplicationContext context: Context): SearchFeatureAPI =
        SearchFeatureAPIImpl(context)

    @Provides
    @Singleton
    fun provideAuthenticationFeatureAPI(@ApplicationContext context: Context): AuthenticationFeatureAPI =
        AuthenticationFeatureAPIImpl(context)

    @Provides
    @Singleton
    fun provideAppNavigationAPI(@ApplicationContext context: Context): BottomNavBarAPI =
        BottomNavBarAPIImpl(context)
}
