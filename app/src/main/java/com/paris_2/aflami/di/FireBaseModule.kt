package com.paris_2.aflami.di

import com.parise_2.firebase.firebase.FireBaseCrashlyticsLogger
import com.parise_2.firebase.repo.Logger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val FireBaseModule = module {
    singleOf(::FireBaseCrashlyticsLogger) bind Logger::class
}
