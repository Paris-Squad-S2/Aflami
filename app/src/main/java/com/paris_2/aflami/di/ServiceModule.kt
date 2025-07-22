package com.paris_2.aflami.di

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.paris_2.datasource.remote.authentication.AuthenticationApi
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.jvm.java

val serviceModule = module{
    single{get<Retrofit>().create(AuthenticationApi::class.java)}
    single{get<Retrofit>().create(RetrofitMovieDetailsApiService::class.java)}
    single{get<Retrofit>().create(RetrofitTvShowDetailsApiService::class.java)}
}