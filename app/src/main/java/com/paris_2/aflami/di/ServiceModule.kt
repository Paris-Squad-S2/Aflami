package com.paris_2.aflami.di

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.paris_2.datasource.remote.user.AuthenticationApi
import com.repository.media.services.GenresApiServices
import com.repository.media.services.MediaApiService
import com.repository.media.services.RetrofitSearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi =
        retrofit.create(AuthenticationApi::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailsApiService(retrofit: Retrofit): RetrofitMovieDetailsApiService =
        retrofit.create(RetrofitMovieDetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideTvShowDetailsApiService(retrofit: Retrofit): RetrofitTvShowDetailsApiService =
        retrofit.create(RetrofitTvShowDetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): RetrofitSearchApiService =
        retrofit.create(RetrofitSearchApiService::class.java)

    @Provides
    @Singleton
    fun provideGenresApiServices(retrofit: Retrofit): GenresApiServices =
        retrofit.create(GenresApiServices::class.java)

    @Provides
    @Singleton
    fun provideMediaApiService(retrofit: Retrofit): MediaApiService =
        retrofit.create(MediaApiService::class.java)

}

