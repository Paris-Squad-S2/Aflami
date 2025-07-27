package com.feature.authentication.authenticationUi.di

import com.feature.authentication.authenticationUi.navigation.AuthenticationDestinations
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigator
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {
    @Provides
    @Singleton
    fun provideAuthenticationNavigator(): AuthenticationNavigator {
        return AuthenticationNavigatorImpl(startGraph = AuthenticationDestinations.AuthenticationGraph1)
    }
}
