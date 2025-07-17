package com.paris_2.aflami.di

import com.datasource.remote.tvShow.TvShowDetailsRemoteDataSourceImpl
import com.datasource.remote.movie.MovieDetailsRemoteDataSourceImpl
import com.datasource.remote.tvShow.service.KtorTvShowDetailsApiService
import com.datasource.remote.tvShow.service.implementation.KtorTvShowDetailsApiServiceImpl
import com.datasource.remote.movie.service.KtorMovieDetailsApiService
import com.datasource.remote.movie.service.KtorMovieDetailsApiServiceImpl
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.movie.dataSource.remote.MovieDetailsRemoteDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mediaDetailsModule: Module = module {
    single<KtorMovieDetailsApiService> { KtorMovieDetailsApiServiceImpl(httpClient = get()) }
    single<KtorTvShowDetailsApiService> { KtorTvShowDetailsApiServiceImpl(httpClient = get()) }
    singleOf(::MovieDetailsRemoteDataSourceImpl) { bind<MovieDetailsRemoteDataSource>() }
    singleOf(::TvShowDetailsRemoteDataSourceImpl) { bind<TvShowDetailsRemoteDataSource>() }
}
