package com.feature.mediaDetails.mediaDetailsUi.di

import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.repository.TvShowRepository
import com.repository.movie.repository.MovieRepositoryImpl
import com.repository.tvshow.repository.TvShowRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(),get(),get(),get(),get()) }
    single<TvShowRepository> { TvShowRepositoryImpl(get()) }
}