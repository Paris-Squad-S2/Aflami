package com.paris_2.aflami.di

import com.repository.home.GenresDataSourceImpl
import com.repository.home.MediaDataSourceImpl
import com.repository.home.datasource.remote.GenresRemoteDataSource
import com.repository.home.datasource.remote.MediaRemoteDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeRemoteDataSourceModule: Module = module{
    singleOf(::GenresDataSourceImpl) { bind<GenresRemoteDataSource>()}
    singleOf(::MediaDataSourceImpl){bind<MediaRemoteDataSource>()}
}