package com.paris_2.aflami.di

import android.content.Context
import com.paris_2.aflami.AuthInterceptor
import com.paris_2.aflami.BuildConfig
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import com.repository.media.services.GenresApiServices
import com.repository.media.services.MediaApiService
import com.repository.util.NetworkConnectionChecker as CommonNetworkConnectionChecker
import com.repository.movie.util.NetworkConnectionChecker as MovieNetworkConnectionChecker
import com.repository.media.util.NetworkConnectionChecker as HomeNetworkConnectionChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideCommonNetworkConnectionChecker(@ApplicationContext context: Context): CommonNetworkConnectionChecker =
        CommonNetworkConnectionChecker(context)

    @Provides
    @Singleton
    fun provideMovieNetworkConnectionChecker(@ApplicationContext context: Context): MovieNetworkConnectionChecker =
        MovieNetworkConnectionChecker(context)

    @Provides
    @Singleton
    fun provideHomeNetworkConnectionChecker(@ApplicationContext context: Context): HomeNetworkConnectionChecker =
        HomeNetworkConnectionChecker(context)

    @Provides
    @Singleton
    fun provideAuthInterceptor(localDataSource: AuthenticationLocalDataSource): AuthInterceptor =
        AuthInterceptor(localDataSource)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()

}
