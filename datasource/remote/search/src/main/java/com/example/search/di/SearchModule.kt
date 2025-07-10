package com.example.search.di

import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.example.search.GenresRemoteDataSourceImp
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.example.search.SearchRemoteDataSourceImpl
import com.example.search.service.contract.GenresApiServices
import com.example.search.service.implementation.KtorGenresApiServices
import com.example.search.service.implementation.KtorSearchApiService
import com.example.search.service.contract.SearchApiService
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val SearchModule: Module = module {

    single<SearchApiService> {
        KtorSearchApiService(
            httpClient = get(), baseUrl = "https://api.themoviedb.org/3/"
        )
    }
    single<GenresApiServices> {
        KtorGenresApiServices(
            httpClient = get(), baseUrl = "https://api.themoviedb.org/3/"
        )
    }
    singleOf(::SearchRemoteDataSourceImpl) { bind<com.repository.search.dataSource.remote.SearchRemoteDataSource>() }
    singleOf(::GenresRemoteDataSourceImp) { bind<com.repository.search.dataSource.remote.GenresRemoteDataSource>() }
}