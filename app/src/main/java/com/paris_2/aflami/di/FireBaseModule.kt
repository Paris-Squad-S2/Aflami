package com.paris_2.aflami.di

import com.parise_2.firebase.firebase.FireBaseCrashlyticsLogger
import com.parise_2.firebase.repo.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {
    @Provides
    @Singleton
    fun provideLogger(impl: FireBaseCrashlyticsLogger): Logger = impl
}

