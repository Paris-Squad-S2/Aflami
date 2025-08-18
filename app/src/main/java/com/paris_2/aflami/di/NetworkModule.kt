package com.paris_2.aflami.di

import android.content.Context
import com.paris_2.aflami.BuildConfig
import com.paris_2.datasource.remote.user.UserAuthInterceptor
import com.paris_2.repository.user.dataSource.local.AuthenticationLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton
import com.repository.guessgame.utils.NetworkConnectionChecker as GuessGameConnectionChecker
import com.repository.media.util.NetworkConnectionChecker as HomeNetworkConnectionChecker
import com.repository.util.NetworkConnectionChecker as CommonNetworkConnectionChecker

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCommonNetworkConnectionChecker(@ApplicationContext context: Context): CommonNetworkConnectionChecker =
        CommonNetworkConnectionChecker(context)

    @Provides
    @Singleton
    fun provideHomeNetworkConnectionChecker(@ApplicationContext context: Context): HomeNetworkConnectionChecker =
        HomeNetworkConnectionChecker(context)

    @Provides
    @Singleton
    fun provideGuessGameConnectionChecker(@ApplicationContext context: Context): GuessGameConnectionChecker =
        GuessGameConnectionChecker(context)

    @Provides
    @Singleton
    fun provideAuthInterceptor(localDataSource: AuthenticationLocalDataSource): UserAuthInterceptor =
        UserAuthInterceptor { localDataSource.getSessionId() }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: UserAuthInterceptor): OkHttpClient {
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
