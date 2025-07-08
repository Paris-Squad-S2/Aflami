package com.example.search.di

import com.example.search.SearchRemoteDataSource
import com.example.search.SearchRemoteDataSourceImpl
import com.example.search.api.KtorSearchApiService
import com.example.search.api.SearchApiService
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val SearchModule: Module = module {

    single<SearchApiService> {KtorSearchApiService(httpClient = get(), baseUrl = "https://api.themoviedb.org/3/") }
    singleOf(::SearchRemoteDataSourceImpl) { bind<SearchRemoteDataSource>() }
}
