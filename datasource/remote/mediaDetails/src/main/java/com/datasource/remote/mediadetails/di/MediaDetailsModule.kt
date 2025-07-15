package com.datasource.remote.mediadetails.di

import com.datasource.remote.mediadetails.MovieDetailsRemoteDataSource
import com.datasource.remote.mediadetails.MovieDetailsRemoteDataSourceImpl
import com.datasource.remote.mediadetails.TvShowDetailsRemoteDataSource
import com.datasource.remote.mediadetails.TvShowDetailsRemoteDataSourceImpl
import com.datasource.remote.mediadetails.service.KtorMovieDetailsApiService
import com.datasource.remote.mediadetails.service.KtorTvShowDetailsApiService
import com.datasource.remote.mediadetails.service.implementation.KtorMediaDetailsApiServiceImpl
import com.datasource.remote.mediadetails.service.implementation.KtorTvShowDetailsApiServiceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mediaDetailsModule: Module = module {
    single<KtorMovieDetailsApiService> { KtorMediaDetailsApiServiceImpl(httpClient = get()) }
    single<KtorTvShowDetailsApiService> { KtorTvShowDetailsApiServiceImpl(httpClient = get()) }
    singleOf(::MovieDetailsRemoteDataSourceImpl) { bind<MovieDetailsRemoteDataSource>() }
    singleOf(::TvShowDetailsRemoteDataSourceImpl) { bind<TvShowDetailsRemoteDataSource>() }
}
