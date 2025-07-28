package com.paris_2.aflami.di

import com.datasource.remote.tvShow.TvShowDetailsRemoteDataSourceImpl
import com.datasource.remote.movie.MovieDetailsRemoteDataSourceImpl
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaDetailsModule {
    @Provides
    @Singleton
    fun provideMovieDetailsRemoteDataSource(impl: MovieDetailsRemoteDataSourceImpl): MovieDetailsRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideTvShowDetailsRemoteDataSource(impl: TvShowDetailsRemoteDataSourceImpl): TvShowDetailsRemoteDataSource = impl
}

