package com.paris_2.aflami

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.paris_2.aflami.di.FeatureAPIModule
import com.paris_2.aflami.di.NetworkModule
import com.paris_2.aflami.di.SearchRemoteDataSourceModule
import com.paris_2.aflami.di.dataSourceModule
import com.paris_2.aflami.di.homeRemoteDataSourceModule
import com.paris_2.aflami.di.mediaDetailsModule
import com.paris_2.aflami.di.repositoryModule
import com.paris_2.aflami.di.roomModule
import com.paris_2.aflami.di.serviceModule
import com.paris_2.aflami.di.useCaseModule
import com.paris_2.aflami.di.viewModelModule
import com.paris_2.aflami.di.workManagerModule
import com.repository.home.util.HomeNetworkConnectionChecker
import com.repository.movie.util.MovieNetworkConnectionChecker
import com.repository.search.util.SearchNetworkConnectionChecker
import com.repository.util.TvNetworkConnectionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class AflamiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
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

        TvNetworkConnectionChecker(this)
        SearchNetworkConnectionChecker(this)
        MovieNetworkConnectionChecker(this)
        HomeNetworkConnectionChecker(this)
    }
}
