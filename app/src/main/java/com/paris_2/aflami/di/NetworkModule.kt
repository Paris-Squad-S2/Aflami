package com.paris_2.aflami.di

import com.feature.search.searchUi.BuildConfig
import com.paris_2.aflami.AuthInterceptor
import com.paris_2.repository.authentication.dataSource.local.AuthenticationLocalDataSource
import com.repository.home.GenresApiServices
import com.repository.home.MediaApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val NetworkModule = module {

    single { com.repository.search.util.NetworkConnectionChecker(androidApplication().applicationContext) }
    single { com.repository.util.NetworkConnectionChecker(androidApplication().applicationContext) }
    single { com.repository.movie.util.NetworkConnectionChecker(androidApplication().applicationContext) }
    single { com.repository.home.util.NetworkConnectionChecker(androidApplication().applicationContext) }

    single { AuthInterceptor(get<AuthenticationLocalDataSource>()) }

    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<MediaApiService> {
        get<Retrofit>().create(MediaApiService::class.java)
    }
    single<GenresApiServices> {
        get<Retrofit>().create(GenresApiServices::class.java)
    }

}