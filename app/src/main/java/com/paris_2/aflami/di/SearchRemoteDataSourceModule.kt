package com.paris_2.aflami.di

import com.repository.media.SearchRemoteDataSourceImpl
import com.repository.media.datasource.remote.SearchRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchRemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource = impl

}

