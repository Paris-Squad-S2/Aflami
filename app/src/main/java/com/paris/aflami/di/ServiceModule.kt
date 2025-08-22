package com.paris.aflami.di

import com.datasource.remote.lists.service.ListApiService
import com.paris.datasource.remote.user.UserApi
import com.repository.media.services.ActorPopularityApiService
import com.repository.media.services.MediaApiService
import com.repository.media.services.MovieApiService
import com.repository.media.services.TvShowDetailsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthenticationApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailsApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideTvShowDetailsApiService(retrofit: Retrofit): TvShowDetailsApiService =
        retrofit.create(TvShowDetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideMediaApiService(retrofit: Retrofit): MediaApiService =
        retrofit.create(MediaApiService::class.java)

    @Provides
    fun provideRetrofitListApiService(retrofit: Retrofit): ListApiService {
        return retrofit.create(ListApiService::class.java)
    }
    @Provides
    fun provideActorPopularityApiService(retrofit: Retrofit): ActorPopularityApiService {
        return retrofit.create(ActorPopularityApiService::class.java)
    }
}

