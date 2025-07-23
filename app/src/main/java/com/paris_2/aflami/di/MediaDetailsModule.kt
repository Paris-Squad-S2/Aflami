package com.paris_2.aflami.di

import com.datasource.remote.tvShow.TvShowDetailsRemoteDataSourceImpl
import com.datasource.remote.movie.MovieDetailsRemoteDataSourceImpl
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mediaDetailsModule: Module = module {
    singleOf(::MovieDetailsRemoteDataSourceImpl) { bind<MovieDetailsRemoteDataSource>() }
    singleOf(::TvShowDetailsRemoteDataSourceImpl) { bind<TvShowDetailsRemoteDataSource>() }
}
