package com.paris_2.aflami.di

import com.paris_2.datasource.remote.authentication.AuthenticationApi
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.jvm.java

val serviceModule = module{
    single{get<Retrofit>().create(AuthenticationApi::class.java)}
}