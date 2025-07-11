package com.feature.search.searchUi.di

import com.domain.search.repository.CategoriesRepository
import com.domain.search.repository.CountryRepository
import com.domain.search.repository.SearchHistoryRepository
import com.domain.search.repository.SearchMediaRepository
import com.repository.search.repository.CategoriesRepositoryImpl
import com.repository.search.repository.CountryRepositoryImpl
import com.repository.search.repository.SearchHistoryRepositoryImpl
import com.repository.search.repository.SearchMediaRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
    single<CategoriesRepository> { CategoriesRepositoryImpl(get(), get(), get()) }
    single<CountryRepository> { CountryRepositoryImpl(get()) }
    single<SearchMediaRepository> { SearchMediaRepositoryImpl(get(), get(), get(), get()) }

}