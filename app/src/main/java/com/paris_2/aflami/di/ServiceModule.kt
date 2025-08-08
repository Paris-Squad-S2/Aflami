package com.paris_2.aflami.di

import com.datasource.remote.lists.service.RetrofitListApiService
import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.paris_2.datasource.remote.user.UserApi
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
    fun provideAuthenticationApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

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

    @Provides
    fun provideRetrofitListApiService(retrofit: Retrofit): RetrofitListApiService {
        return retrofit.create(RetrofitListApiService::class.java)
    }
}

