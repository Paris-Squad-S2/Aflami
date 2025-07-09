package com.feature.search.searchUi.di

import com.datasource.local.search.datasource.CountriesLocalDataSourceImpl
import com.datasource.local.search.datasource.GenresLocalDataSourceImpl
import com.datasource.local.search.datasource.HistoryLocalDataSourceImpl
import com.datasource.local.search.datasource.MediaLocalDataSourceImpl
import com.repository.search.dataSource.CountriesLocalDataSource
import com.repository.search.dataSource.GenresLocalDataSource
import com.repository.search.dataSource.HistoryLocalDataSource
import com.repository.search.dataSource.MediaLocalDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<HistoryLocalDataSource> { HistoryLocalDataSourceImpl(get()) }
    single<MediaLocalDataSource> { MediaLocalDataSourceImpl(get()) }
    single<CountriesLocalDataSource> { CountriesLocalDataSourceImpl(get()) }
    single<GenresLocalDataSource> { GenresLocalDataSourceImpl(get()) }
}