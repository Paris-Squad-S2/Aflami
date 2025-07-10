package com.example.search.di

import com.example.search.GenresRemoteDataSource
import com.example.search.GenresRemoteDataSourceImp
import com.example.search.SearchRemoteDataSource
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
    singleOf(::SearchRemoteDataSourceImpl) { bind<SearchRemoteDataSource>() }
    singleOf(::GenresRemoteDataSourceImp) { bind<GenresRemoteDataSource>() }
}