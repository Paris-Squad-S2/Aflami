package com.paris_2.aflami.di

import com.datasource.remote.movie.service.RetrofitMovieDetailsApiService
import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.paris_2.datasource.remote.authentication.AuthenticationApi
import com.repository.search.service.implementation.RetrofitGenresApiServices
import com.repository.search.service.implementation.RetrofitSearchApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module{
    single{get<Retrofit>().create(AuthenticationApi::class.java)}
    single{get<Retrofit>().create(RetrofitMovieDetailsApiService::class.java)}
    single{get<Retrofit>().create(RetrofitTvShowDetailsApiService::class.java)}
    single{get<Retrofit>().create(RetrofitSearchApiService::class.java)}
    single{get<Retrofit>().create(RetrofitGenresApiServices::class.java)}
}