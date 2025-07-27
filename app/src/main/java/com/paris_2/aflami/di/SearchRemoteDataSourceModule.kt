package com.paris_2.aflami.di

import com.repository.search.GenresRemoteDataSourceImp
import com.repository.search.SearchRemoteDataSourceImpl
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
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

    @Provides
    @Singleton
    fun provideGenresRemoteDataSource(impl: GenresRemoteDataSourceImp): GenresRemoteDataSource = impl
}

