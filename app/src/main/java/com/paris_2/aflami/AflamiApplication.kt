package com.paris_2.aflami

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AflamiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}


        /*        startKoin {
                    androidLogger()
                    androidContext(this@AflamiApplication)
                    modules(
                        FeatureAPIModule,
                        viewModelModule,
                        roomModule,
                        workManagerModule,
                        dataSourceModule,
                        useCaseModule,
                        repositoryModule,
                        NetworkModule,
                        SearchRemoteDataSourceModule,
                        mediaDetailsModule,
                        serviceModule,
                        homeRemoteDataSourceModule
                    )
                }

                val workerFactory: WorkerFactory = getKoin().get()
                val configuration = Configuration.Builder()
                    .setWorkerFactory(workerFactory)
                    .setMinimumLoggingLevel(Log.DEBUG)
                    .build()

                WorkManager.initialize(this, configuration)

                val networkChecker1: NetworkConnectionChecker = getKoin().get()
                val networkChecker2: com.repository.movie.util.NetworkConnectionChecker = getKoin().get()
                val networkChecker3: com.repository.util.NetworkConnectionChecker = getKoin().get()
                val networkChecker4: com.repository.home.util.NetworkConnectionChecker = getKoin().get()
                networkChecker1.startChecker()
                networkChecker2.startChecker()
                networkChecker3.startChecker()
                networkChecker4.startChecker()
            }*/

