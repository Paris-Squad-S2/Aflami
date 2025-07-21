package com.paris_2.aflami.di

import android.util.Log
import com.paris_2.home.MediaApiService
import com.feature.search.searchUi.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
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
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    private val TAG = "KtorHttpClient"
                    override fun log(message: String) {
                        Log.i(TAG, message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            install(DefaultRequest) {
                header(HttpHeaders.Authorization, "Bearer ${BuildConfig.API_TOKEN}")
                 url("https://api.themoviedb.org/3/")
            }
        }
    }
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
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
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single<MediaApiService> {
        get<Retrofit>().create(MediaApiService::class.java)
    }
}