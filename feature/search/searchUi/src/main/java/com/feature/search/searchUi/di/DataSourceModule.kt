package com.feature.search.searchUi.di

import com.datasource.local.search.HistoryLocalDataSourceImpl
import com.datasource.local.search.MediaLocalDataSourceImpl
import com.repository.search.dataSource.HistoryLocalDataSource
import com.repository.search.dataSource.MediaLocalDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<HistoryLocalDataSource> { HistoryLocalDataSourceImpl(get()) }
    single<MediaLocalDataSource> { MediaLocalDataSourceImpl(get()) }
}