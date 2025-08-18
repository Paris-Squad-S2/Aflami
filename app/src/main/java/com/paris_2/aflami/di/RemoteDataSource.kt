package com.paris_2.aflami.di

import com.repository.media.MovieRemoteDataSourceImpl
import com.repository.media.TvShowDetailsRemoteDataSourceImpl
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.media.ActorPopularityRemoteDataSourceImpl
import com.repository.media.GenresRemoteDataSourceImpl
import com.repository.media.MediaRemoteDataSourceImpl
import com.repository.media.SearchRemoteDataSourceImpl
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.media.datasource.remote.MovieRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindGenresRemoteDataSource(
        impl: GenresRemoteDataSourceImpl
    ): GenresRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMediaRemoteDataSource(
        impl: MediaRemoteDataSourceImpl
    ): MediaRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        impl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTvShowDetailsRemoteDataSource(
        impl: TvShowDetailsRemoteDataSourceImpl
    ): TvShowDetailsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSearchRemoteDataSource(
        impl: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindActorPopularityRemoteDataSource(
        impl : ActorPopularityRemoteDataSourceImpl
    ) : ActorPopularityRemoteDataSource
}