package com.paris_2.aflami

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.paris_2.dataSource.local.user.LanguageLocalDataSourceImp
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        val locale = runBlocking {
            LanguageLocalDataSourceImp(base).getLanguage().first()
        }
        updateAppLocale(base, locale)
    }

    private fun updateAppLocale(context: Context, locale: String) {
        Locale.setDefault(Locale(locale))
        val config = context.resources.configuration
        config.setLocale(Locale(locale))
        @Suppress("DEPRECATION")
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
}
