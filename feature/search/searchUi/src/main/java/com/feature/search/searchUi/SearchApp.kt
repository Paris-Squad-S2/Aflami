package com.feature.search.searchUi

import android.app.Application
import com.feature.search.searchUi.di.NetworkModule
import com.example.search.di.SearchModule
import com.feature.search.searchUi.di.dataSourceModule
import com.feature.search.searchUi.di.repositoryModule
import com.feature.search.searchUi.di.roomModule
import com.feature.search.searchUi.di.useCaseModule
import com.feature.search.searchUi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SearchApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SearchApp)
            modules(
                viewModelModule,
                roomModule,
                dataSourceModule,
                useCaseModule,
                repositoryModule,
                NetworkModule,
                SearchModule
            )
        }
    }
}