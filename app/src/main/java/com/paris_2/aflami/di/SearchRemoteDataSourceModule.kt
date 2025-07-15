package com.paris_2.aflami.di

import com.example.search.GenresRemoteDataSourceImp
import com.example.search.SearchRemoteDataSourceImpl
import com.example.search.service.contract.GenresApiServices
import com.example.search.service.contract.SearchApiService
import com.example.search.service.implementation.KtorGenresApiServices
import com.example.search.service.implementation.KtorSearchApiService
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val SearchRemoteDataSourceModule: Module = module {

    single<SearchApiService> { KtorSearchApiService(httpClient = get()) }
    single<GenresApiServices> { KtorGenresApiServices(httpClient = get()) }
    singleOf(::SearchRemoteDataSourceImpl) { bind<SearchRemoteDataSource>() }
    singleOf(::GenresRemoteDataSourceImp) { bind<GenresRemoteDataSource>() }
}