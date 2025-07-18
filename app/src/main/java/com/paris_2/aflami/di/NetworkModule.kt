package com.paris_2.aflami.di

import android.util.Log
import com.feature.search.searchUi.BuildConfig
import com.repository.search.util.NetworkConnectionChecker
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val NetworkModule = module {

    single { NetworkConnectionChecker(androidApplication().applicationContext) }
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
}