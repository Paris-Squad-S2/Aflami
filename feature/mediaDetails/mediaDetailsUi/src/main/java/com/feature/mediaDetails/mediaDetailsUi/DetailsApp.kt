package com.feature.mediaDetails.mediaDetailsUi

import android.app.Application
import com.feature.mediaDetails.mediaDetailsUi.di.NetworkModule
import com.feature.mediaDetails.mediaDetailsUi.di.dataSourceModule
import com.feature.mediaDetails.mediaDetailsUi.di.repositoryModule
import com.feature.mediaDetails.mediaDetailsUi.di.roomModule
import com.feature.mediaDetails.mediaDetailsUi.di.useCaseModule
import com.feature.mediaDetails.mediaDetailsUi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DetailsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DetailsApp)
            modules(
                viewModelModule,
                roomModule,
                dataSourceModule,
                useCaseModule,
                repositoryModule,
                NetworkModule,
            )
        }
    }
}