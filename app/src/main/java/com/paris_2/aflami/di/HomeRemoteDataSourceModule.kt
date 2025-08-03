package com.paris_2.aflami.di

import com.repository.home.GenresDataSourceImpl
import com.repository.home.MediaDataSourceImpl
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideGenresRemoteDataSource(impl: GenresDataSourceImpl): GenresRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideMediaRemoteDataSource(impl: MediaDataSourceImpl): MediaRemoteDataSource = impl
}

