package com.paris_2.aflami.di

import com.domain.home.repository.MediaRepository
import com.domain.home.repository.MoviesCategoriesRepository
import com.domain.mediaDetails.repository.MovieRepository
import com.domain.mediaDetails.repository.TvShowRepository
import com.domain.search.repository.CategoriesRepository
import com.domain.search.repository.CountryRepository
import com.domain.search.repository.GenresInteractionRepository
import com.domain.search.repository.SearchHistoryRepository
import com.domain.search.repository.SearchMediaRepository
import com.paris_2.domain.authentication.repository.AuthenticationRepository
import com.paris_2.repository.authentication.repository.AuthenticationRepositoryImpl
import com.repository.home.repository.MediaRepositoryImpl
import com.repository.home.repository.MoviesCategoriesRepositoryImpl
import com.repository.movie.repository.MovieRepositoryImpl
import com.repository.repository.TvShowRepositoryImpl
import com.repository.search.repository.CategoriesRepositoryImpl
import com.repository.search.repository.CountryRepositoryImpl
import com.repository.search.repository.GenresInteractionRepositoryImpl
import com.repository.search.repository.SearchHistoryRepositoryImpl
import com.repository.search.repository.SearchMediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(impl: SearchHistoryRepositoryImpl): SearchHistoryRepository = impl

    @Provides
    @Singleton
    fun provideCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository = impl

    @Provides
    @Singleton
    fun provideCountryRepository(impl: CountryRepositoryImpl): CountryRepository = impl

    @Provides
    @Singleton
    fun provideSearchMediaRepository(impl: SearchMediaRepositoryImpl): SearchMediaRepository = impl

    @Provides
    @Singleton
    fun provideTvShowRepository(impl: TvShowRepositoryImpl): TvShowRepository = impl

    @Provides
    @Singleton
    fun provideMovieRepository(impl: MovieRepositoryImpl): MovieRepository = impl

    @Provides
    @Singleton
    fun provideGenresInteractionRepository(impl: GenresInteractionRepositoryImpl): GenresInteractionRepository = impl

    @Provides
    @Singleton
    fun provideMediaRepository(impl: MediaRepositoryImpl): MediaRepository = impl

    @Provides
    @Singleton
    fun provideAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository = impl

    @Provides
    @Singleton
    fun provideMoviesCategoriesRepository(impl: MoviesCategoriesRepositoryImpl): MoviesCategoriesRepository = impl
}

