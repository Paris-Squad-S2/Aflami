package com.feature.search.searchUi.navigation

import android.app.Application
import com.feature.search.searchUi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SearchApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SearchApp)
            modules(viewModelModule)
        }
    }
}