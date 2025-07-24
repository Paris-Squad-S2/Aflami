package com.paris_2.aflami.di

import com.repository.search.GenresRemoteDataSourceImp
import com.repository.search.SearchRemoteDataSourceImpl
import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val SearchRemoteDataSourceModule: Module = module {
    singleOf(::SearchRemoteDataSourceImpl) { bind<SearchRemoteDataSource>() }
    singleOf(::GenresRemoteDataSourceImp) { bind<GenresRemoteDataSource>() }
}