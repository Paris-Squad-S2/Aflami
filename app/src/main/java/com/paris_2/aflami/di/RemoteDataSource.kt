package com.paris_2.aflami.di

import com.datasource.remote.movie.MovieRemoteDataSourceImpl
import com.datasource.remote.tvShow.TvShowDetailsRemoteDataSourceImpl
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.media.GenresDataSourceImpl
import com.repository.media.MediaDataSourceImpl
import com.repository.media.SearchRemoteDataSourceImpl
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.movie.dataSource.remote.MovieRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSource {
    @Provides
    @Singleton
    fun provideGenresRemoteDataSource(impl: GenresDataSourceImpl): GenresRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideMediaRemoteDataSource(impl: MediaDataSourceImpl): MediaRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideMovieDetailsRemoteDataSource(impl: MovieRemoteDataSourceImpl): MovieRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideTvShowDetailsRemoteDataSource(impl: TvShowDetailsRemoteDataSourceImpl): TvShowDetailsRemoteDataSource = impl

    @Provides
    @Singleton
    fun provideSearchRemoteDataSource(impl: SearchRemoteDataSourceImpl): SearchRemoteDataSource = impl
}