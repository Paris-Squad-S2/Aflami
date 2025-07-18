package com.paris_2.aflami.di

import com.repository.search.GenresRemoteDataSourceImp
import com.repository.search.SearchRemoteDataSourceImpl
import com.repository.search.service.contract.GenresApiServices
import com.repository.search.service.contract.SearchApiService
import com.repository.search.service.implementation.KtorGenresApiServices
import com.repository.search.service.implementation.KtorSearchApiService
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