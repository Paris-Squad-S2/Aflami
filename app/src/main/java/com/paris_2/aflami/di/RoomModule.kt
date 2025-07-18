package com.paris_2.aflami.di

import com.datasource.local.search.SearchDatabase
import com.datasource.local.search.dao.CountryDao
import com.datasource.local.search.dao.GenresDao
import com.datasource.local.search.dao.GenresUserInteractionDao
import com.datasource.local.search.dao.MediaDao
import com.datasource.local.search.dao.SearchHistoryDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { androidApplication().applicationContext }
    single { SearchDatabase.getInstance(androidApplication().applicationContext) }
    single<SearchHistoryDao> { get<SearchDatabase>().searchHistoryDao() }
    single<MediaDao> { get<SearchDatabase>().mediaDao() }
    single<CountryDao> { get<SearchDatabase>().countryDao() }
    single<GenresDao> { get<SearchDatabase>().genresDao() }
    single<GenresUserInteractionDao> { get<SearchDatabase>().genreUserInteractionDao() }
}