package com.paris_2.aflami.di

import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.search.repository.CategoriesRepository
import com.domain.search.repository.CountryRepository
import com.domain.search.repository.GenresInteractionRepository
import com.domain.search.repository.SearchHistoryRepository
import com.domain.search.repository.SearchMediaRepository
import com.repository.movie.repository.MovieRepositoryImpl
import com.repository.repository.TvShowRepositoryImpl
import com.repository.search.repository.CategoriesRepositoryImpl
import com.repository.search.repository.CountryRepositoryImpl
import com.repository.search.repository.GenresInteractionRepositoryImpl
import com.repository.search.repository.SearchHistoryRepositoryImpl
import com.repository.search.repository.SearchMediaRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SearchHistoryRepositoryImpl) bind SearchHistoryRepository::class
    singleOf(::CategoriesRepositoryImpl) bind CategoriesRepository::class
    singleOf(::CountryRepositoryImpl) bind CountryRepository::class
    singleOf(::SearchMediaRepositoryImpl) bind SearchMediaRepository::class
    singleOf(::TvShowRepositoryImpl) bind TvShowRepository::class
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
    singleOf(::GenresInteractionRepositoryImpl) bind GenresInteractionRepository::class
}