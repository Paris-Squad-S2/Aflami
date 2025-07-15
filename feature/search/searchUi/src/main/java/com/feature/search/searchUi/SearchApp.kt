package com.feature.search.searchUi

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.datasource.remote.mediadetails.di.mediaDetailsModule
import com.example.search.di.SearchModule
import com.feature.search.searchUi.di.NetworkModule
import com.feature.search.searchUi.di.dataSourceModule
import com.feature.search.searchUi.di.repositoryModule
import com.feature.search.searchUi.di.roomModule
import com.feature.search.searchUi.di.useCaseModule
import com.feature.search.searchUi.di.viewModelModule
import com.feature.search.searchUi.di.workManagerModule
import com.repository.search.NetworkConnectionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class SearchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SearchApp)
            modules(
                viewModelModule,
                roomModule,
                workManagerModule,
                dataSourceModule,
                useCaseModule,
                repositoryModule,
                NetworkModule,
                SearchModule,
                mediaDetailsModule
            )
        }

        val workerFactory: WorkerFactory = getKoin().get()
        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

        WorkManager.initialize(this, configuration)

        val networkChecker: NetworkConnectionChecker = getKoin().get()
        networkChecker.startChecker()
    }


}