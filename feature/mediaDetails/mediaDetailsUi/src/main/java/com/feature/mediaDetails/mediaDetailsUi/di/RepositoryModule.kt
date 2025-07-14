package com.feature.mediaDetails.mediaDetailsUi.di

import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.repository.TvShowRepository
import com.example.movie.repository.MovieRepositoryImpl
import com.example.tvshow.repository.TvShowRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl() }
    single<TvShowRepository> { TvShowRepositoryImpl() }
}