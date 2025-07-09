package com.feature.search.searchUi.di

import com.domain.search.repository.CategoriesRepository
import com.domain.search.repository.CategoriesRepositoryFakeImpl
import com.domain.search.repository.CountryRepository
import com.domain.search.repository.CountryRepositoryFakeImpl
import com.domain.search.repository.SearchHistoryRepository
import com.domain.search.repository.SearchHistoryRepositoryFakeImpl
import com.domain.search.repository.SearchMediaRepository
import com.domain.search.repository.SearchMediaRepositoryFakeImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchHistoryRepository> { SearchHistoryRepositoryFakeImpl() }
    single<CategoriesRepository> { CategoriesRepositoryFakeImpl() }
    single<CountryRepository> { CountryRepositoryFakeImpl() }
    single<SearchMediaRepository> { SearchMediaRepositoryFakeImpl() }

}