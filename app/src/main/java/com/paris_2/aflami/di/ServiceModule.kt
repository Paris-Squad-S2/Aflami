package com.paris_2.aflami.di

import com.datasource.remote.lists.service.ListApiService
import com.datasource.remote.movie.service.MovieApiService
import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.paris_2.datasource.remote.user.UserApi
import com.repository.media.services.ActorPopularityApiService
import com.repository.media.services.GenresApiServices
import com.repository.media.services.MediaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
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
    fun provideMovieDetailsApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideTvShowDetailsApiService(retrofit: Retrofit): RetrofitTvShowDetailsApiService =
        retrofit.create(RetrofitTvShowDetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideGenresApiServices(retrofit: Retrofit): GenresApiServices =
        retrofit.create(GenresApiServices::class.java)

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

