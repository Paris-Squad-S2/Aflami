package com.example.search.di

import com.example.search.GenresRemoteDataSourceImp
import com.example.search.SearchRemoteDataSourceImpl
import com.example.search.service.contract.GenresApiServices
import com.example.search.service.contract.SearchApiService
import com.example.search.service.implementation.KtorGenresApiServices
import com.example.search.service.implementation.KtorSearchApiService
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val SearchModule: Module = module {

    single<SearchApiService> { KtorSearchApiService(httpClient = get()) }
    single<GenresApiServices> { KtorGenresApiServices(httpClient = get()) }
    singleOf(::SearchRemoteDataSourceImpl) { bind<com.repository.search.dataSource.remote.SearchRemoteDataSource>() }
    singleOf(::GenresRemoteDataSourceImp) { bind<com.repository.search.dataSource.remote.GenresRemoteDataSource>() }
}