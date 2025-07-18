package com.paris_2.aflami.di

import com.datasource.local.search.datasource.CountriesLocalDataSourceImpl
import com.datasource.local.search.datasource.GenresInteractionDataSourceImpl
import com.datasource.local.search.datasource.GenresLocalDataSourceImpl
import com.datasource.local.search.datasource.HistoryLocalDataSourceImpl
import com.datasource.local.search.datasource.MediaLocalDataSourceImpl
import com.repository.search.dataSource.local.CountriesLocalDataSource
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<HistoryLocalDataSource> { HistoryLocalDataSourceImpl(get(), get()) }
    single<MediaLocalDataSource> { MediaLocalDataSourceImpl(get()) }
    single<CountriesLocalDataSource> { CountriesLocalDataSourceImpl(get()) }
    single<GenresLocalDataSource> { GenresLocalDataSourceImpl(get()) }
    single<GenresInteractionDataSource> { GenresInteractionDataSourceImpl(get()) }
}