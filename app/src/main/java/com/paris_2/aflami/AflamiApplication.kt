package com.paris_2.aflami

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.repository.media.util.NetworkConnectionChecker as HomeNetworkChecker
import com.repository.movie.util.NetworkConnectionChecker as MovieNetworkChecker
import com.repository.util.NetworkConnectionChecker as GenericNetworkChecker

@HiltAndroidApp
class AflamiApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var movieNetworkChecker: MovieNetworkChecker

    @Inject
    lateinit var genericNetworkChecker: GenericNetworkChecker

    @Inject
    lateinit var homeNetworkChecker: HomeNetworkChecker

    override fun onCreate() {
        super.onCreate()
        movieNetworkChecker.startChecker()
        genericNetworkChecker.startChecker()
        homeNetworkChecker.startChecker()


    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()


}
