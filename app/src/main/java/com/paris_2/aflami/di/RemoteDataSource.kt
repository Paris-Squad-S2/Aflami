package com.paris_2.aflami.di

import com.datasource.remote.movie.MovieRemoteDataSourceImpl
import com.datasource.remote.tvShow.TvShowDetailsRemoteDataSourceImpl
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.media.GenresRemoteDataSourceImpl
import com.repository.media.MediaRemoteDataSourceImpl
import com.repository.media.SearchRemoteDataSourceImpl
import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.datasource.remote.MediaRemoteDataSource
import com.repository.media.datasource.remote.SearchRemoteDataSource
import com.repository.movie.dataSource.remote.MovieRemoteDataSource
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
}