package com.paris.aflami

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import com.repository.guessgame.utils.NetworkConnectionChecker as GuessNetworkChecker
import com.repository.media.util.NetworkConnectionChecker as HomeNetworkChecker

@HiltAndroidApp
class AflamiApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var homeNetworkChecker: HomeNetworkChecker

    @Inject
    lateinit var GuessNetworkChecker: GuessNetworkChecker

    override fun onCreate() {
        super.onCreate()
        homeNetworkChecker.startChecker()
        GuessNetworkChecker.startChecker()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()


}
