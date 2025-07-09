package com.paris_2.aflami

import android.app.Application
import com.paris_2.aflami.di.FireBaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AflamiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AflamiApp)
            modules(
                listOf(
                    FireBaseModule,
                )
            )
        }
    }
}
